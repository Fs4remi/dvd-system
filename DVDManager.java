import java.io.IOException; 

/**
 * Program to display and modify a simple DVD collection
 */

public class DVDManager {
 
	public static void main(String[] args) {

		DVDUserInterface dlInterface;
		DVDCollection dl = new DVDCollection();
		
		dlInterface = new DVDGUI(dl);
		
		String fileName = dlInterface.getFileName();
		String possibleError;
		try {
			possibleError = dl.loadData(fileName);
			// If no errors happened
			if(possibleError.equals("NA")){
				dlInterface.processCommands();
			}
			else{
				dlInterface.showError(possibleError);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
