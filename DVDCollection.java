import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DVDCollection {
	private int numdvds;/** The current number of DVDs in the array */
	private DVD[] dvdArray;/** The array to contain the DVDs */
	private String sourceName; /** The name of the data file that contains dvd data */
	private boolean modified; //Boolean flag to indicate whether the DVD collection was modified since it was
	
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[2];
	}

	public String toString() {
		String number = "numdvds = " + numdvds + "\n";
		String length = "dvdArray.length = " + dvdArray.length + "\n";
		String collector = "";

		// dvdArray[0] = ANGELS AND DEMONS/PG-13/138min
		for (int i = 0; i < numdvds; i++) {
			collector += "dvdArray[" + i + "] =  " + dvdArray[i].toString();
		}
		return number + length + collector;
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		int num_convertion = Integer.parseInt(runningTime);
		int found_index = getIndexOf(title);
		
		if( found_index != -1){
			//System.out.println("\nMODIFY");
			dvdArray[found_index].setRating(rating);
			dvdArray[found_index].setRunningTime(num_convertion);
			modified = true;
		}
		else{	
			//System.out.println("\nADD"); //sorted insert in alphebetical order
			DVD new_add = new DVD(title, rating, num_convertion);
			if(isFull()){
				resize();
				//System.out.println("resizing\n");
			}
			//Ordered INSERT
			int insert_location = insertIndex(title); 
			//System.out.println("insert index: " + insert_location);
			
			//shift content to the right
			for(int i = numdvds; i > insert_location; i--){
				dvdArray[i] = dvdArray[i-1];
			}
			dvdArray[insert_location] = new_add;
			numdvds++;
			modified = true;
		}
	}
	
	public void removeDVD(String title) {
		int found_index = getIndexOf(title);
		System.out.println(found_index);
		if(found_index != -1){
			for(int i = found_index; i < numdvds-1; i++){
				dvdArray[i] = dvdArray[i+1];
			}
			numdvds--;
			modified = true;
		}
	}

	public String getDVDsByRating(String rating) {
		String all = "";

		for(int i = 0; i < numdvds; i++){
			DVD item = dvdArray[i];
			if (rating.equals(item.getRating()))
				all += item.toString();
		}
		return all;
	}

	public int getTotalRunningTime() {
		if (isEmpty())
			return 0;

		int total = 0;
		for(int i = 0; i < numdvds; i++) 
			total +=  dvdArray[i].getRunningTime();
		return total;
	}

	public void loadData(String filename) throws IOException {
		sourceName = filename;

		ArrayList <String> valid_ratings= new ArrayList<String>();
		valid_ratings.add("PG");
		valid_ratings.add("G");
		valid_ratings.add("PG-13");
		valid_ratings.add("R");
		valid_ratings.add("NC-17");

		Path path = Paths.get(sourceName);
		System.out.println(path);

		Scanner scanner = new Scanner(path);
		// read line by line
		while (scanner.hasNextLine()) {
			// process each line
			String line = scanner.nextLine();
			//split the line on , characters
			String[] separated_values = line.split(",");

			if(separated_values.length > 3 || separated_values.length < 3){
				System.out.println("line in file is too long or too short");
				break;
			}
			String name = separated_values[0];
			String rate = separated_values[1];
			String duration= separated_values[2];
			
			if(! valid_ratings.contains(rate)){
				System.out.println("Invalid rating in file found\n");
				break;
			}
			//else
				//System.out.println("Valid rating yay\n");
			if(! duration.matches("(.)*(\\d)(.)*") ){
				System.out.println("Invalid duration in file found\n");
				break;
			}
			//else
				//System.out.println("Valid number duration yay\n");

			addOrModifyDVD(name, rate, duration);
			//System.out.println(line);
			//System.out.println(scanner.hasNextLine());
		}
		scanner.close();
	}
//save the DVDs currently in the array into the same file specified during the load operation, overwriting whatever data was originally there.
	public void save() {
		System.out.println("modified: " + modified);
		if(modified){
			PrintWriter outputStream = null;
			try{
				outputStream = new PrintWriter( new FileOutputStream(sourceName));
				System.out.println("\nOK");
			}catch(FileNotFoundException error){
				System.out.println("Could not open file ot write in "+error);
			}
			for(int i = 0; i < numdvds; i++){
				String line = dvdArray[i].toString().replace('/', ',');
				line = dvdArray[i].toString().replace('\n', ' ');
				outputStream.println(line);
			}
			outputStream.close();
		}
			
	}

	// Additional private helper methods go here:

	private boolean isFull() {
		return numdvds == dvdArray.length;
	}

	private boolean isEmpty() {
		return numdvds == 0;
	}

	private int getIndexOf(String title){
		if(isEmpty())
			return -1;
		for(int i = 0; i < numdvds; i++){
			DVD d = dvdArray[i];
			if(d.getTitle().equals(title))
				return i;
		}
		return -1;
	}

	private int insertIndex(String title){
		int i = 0;
		while( (i < numdvds) && (dvdArray[i].getTitle().compareTo(title) <= 0) ){
			i++;
		}
		return i;
	}

	private void resize(){
		int new_size = dvdArray.length * 2;

		DVD[] temp = new DVD[new_size];

		for(int i = 0; i < numdvds; i++) {
			temp[i] = new DVD(dvdArray[i]);
		}
		dvdArray = temp;
	}

	/*public static void main(String[] args) {
		DVDCollection d = new DVDCollection();
	
		try {
			d.loadData("10366837.txt");
		} catch (IOException error) {
			error.printStackTrace();
		}
		System.out.println(d.toString());
		System.out.println(d.getDVDsByRating("PG"));

		System.out.println(d.toString());
		d.save();
	}*/
}
