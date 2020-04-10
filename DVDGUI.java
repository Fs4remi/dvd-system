import java.util.*;
import javax.swing.*; 
import java.awt.*;

public class DVDGUI{ 
	private DVDCollection dvdlist;
	private ArrayList <String> valid_ratings;
	private GUIMainFrame frame;
	private ButtonBar toolbar;
	private LeftPanel scrolllyList;
	private CenterPanel canvas;
	private RightPanel tiny;

	
	private Font myFont;
	
	public DVDGUI(){
		myFont = new Font("Monospaced Plain", Font.BOLD, 25);

		valid_ratings= new ArrayList<String>();
		valid_ratings.add("PG");
		valid_ratings.add("G");
		valid_ratings.add("PG-13");
		valid_ratings.add("R");
		valid_ratings.add("NC-17");
		// doSave();
	}

	public void launch(DVDCollection dl){
		dvdlist = dl;
		frame = new GUIMainFrame(600, 500);
		toolbar = new ButtonBar();
		canvas = new CenterPanel();
		tiny = new RightPanel(dvdlist.getTotalRunningTime());
		scrolllyList= new LeftPanel(dvdlist.getTitles());
		
		frame.add(scrolllyList, BorderLayout.LINE_START);
		frame.add(toolbar,  BorderLayout.SOUTH);
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(tiny, BorderLayout.EAST);

		scrolllyList.setListener(new componentListener(){
			public void informationEmitted(String info) {
				var searchResult = dvdlist.getDVDDetail(info);
				if(searchResult != null){
					canvas.showDetails(searchResult);
				}
			}
		});

		toolbar.setListener(new componentListener(){
			public void informationEmitted(String info) {
				//1. Update the dvdcollection
				//2. Update the leftpanel list
				//3. Update the total time
				//4. Save
				if(info.equals("Add/Modify")){
					doAddOrModifyDVD();
				}	
				else if(info.equals("Remove")){
					doRemoveDVD();
				}	
				scrolllyList.updateList(dvdlist.getTitles());
				tiny.updateTime(dvdlist.getTotalRunningTime());
			}
		});

		tiny.setListener(new componentListener(){
			public void informationEmitted(String info) {
				doGetDVDsByRating();
			}
			
		});

		frame.setVisible(true);
	}
	private void checkModifications(){
		if(dvdlist.changed()){
			showError("There are unsaved changes");
		}
		else{
			System.exit(0);
		}
	}

	private void doSave() {
		
		dvdlist.save();
		JOptionPane.showMessageDialog(
			null, 
			"Changes saved to collection",
			"Success message",
			1
		);
	}

	private void doAddOrModifyDVD() {
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(64, 191, 239, 225));
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter title");
        label.setForeground(new Color(55, 74, 93));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(myFont);
        label.setBounds(0, 0, 300, 300);
		panel.add(label);

		String frameTitle = "Adding New DVD"; 

		// Request the title
		String title = JOptionPane.showInputDialog(
			null, 
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE
		);
		// if cancel button was chosen
		if (title == null) {
			return; 
		}
		label.setText("Nothing was entered, type in the title");
		
		// if nothing was typed in
		while(title.length() == 0){
			title = JOptionPane.showInputDialog(
				null,
				panel,
				frameTitle,
				JOptionPane.QUESTION_MESSAGE
			);
		}
		title = title.toUpperCase();
		
		// Request the rating
		label.setText("Enter rating for " + title);
		String rating = JOptionPane.showInputDialog(
			null,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE 
		);
		if (rating == null) {
			return;		// dialog was cancelled
		}
		label.setText("Please enter a valid rating for " + title);		
		while(!valid_ratings.contains(rating.toUpperCase())){
			rating = JOptionPane.showInputDialog(
			null,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE 
		);
		}
		rating = rating.toUpperCase();
		
		// Request the running time
		label.setText("Enter running time for " + title);
		String time = JOptionPane.showInputDialog(
			null,
			panel,
			JOptionPane.QUESTION_MESSAGE
		);
		if (time == null) {
			return;
		}
		label.setText("Enter running time for " + title + " using numbers");
		while(! time.matches("^\\d+$") ){ //(.)*(\\d)*(.)
			time = JOptionPane.showInputDialog(
			null,
			panel,
			JOptionPane.QUESTION_MESSAGE
			);
		}	
		
		// Add or modify the DVD with valid rating and time 
		dvdlist.addOrModifyDVD(title, rating, time);
	}
	
	private void doRemoveDVD() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(210,105,30, 225));
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter title");
        label.setForeground(new Color(0,0,139));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(myFont);
        label.setBounds(0, 0, 300, 300);
		panel.add(label);
		String frameTitle = "Removing a DVD"; 

		// Request the title
		String title = JOptionPane.showInputDialog(
			null,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE
		);
		if (title == null) {
			return;		// dialog was cancelled
		}

		label.setText("Nothing was entered, type in the title");
		while(title.length() == 0){
			title = JOptionPane.showInputDialog(
				null,
				panel,
				frameTitle,
				JOptionPane.QUESTION_MESSAGE
			);
		}
		title = title.toUpperCase();
		
		// Remove the matching DVD if found
		dvdlist.removeDVD(title);
	}
	
	private void doGetDVDsByRating() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(127,255,0, 225));
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter rating");
        label.setForeground(new Color(165,42,42));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(myFont);
        label.setBounds(0, 0, 300, 300);
		panel.add(label);
		 
		String frameTitle = "Ratings";

		// Request the rating
		String rating = JOptionPane.showInputDialog(
			null,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE
		);
		if (rating == null) {
			return;		// dialog was cancelled
		}
		label.setText("Enter a valid rating, eg PG-13");
		while(! valid_ratings.contains(rating.toUpperCase()) || rating.length() == 0){
			rating = JOptionPane.showInputDialog(
			null,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE
			);
		}
		rating = rating.toUpperCase();
		
		String results = dvdlist.getDVDsByRating(rating);
		System.out.println(results);
		String [] separatedResults = results.split("\n");
		System.out.println(separatedResults[0]);

		if(separatedResults.length > 0) {
			scrolllyList.updateList(separatedResults);
		}
		else{
			JOptionPane.showMessageDialog(
			null,
			"There are no movies with " + rating + " rating",
			frameTitle,
			JOptionPane.INFORMATION_MESSAGE
			);
		}
	}
	
	public String getFileName(){
		
			
		String fileName;
		fileName = JOptionPane.showInputDialog(
			null,
			"Enter name of file",
			"Collection name",
			JOptionPane.QUESTION_MESSAGE
		);

		if(fileName == null)
			System.exit(0);
		
		while(fileName.length() == 0){
			fileName = JOptionPane.showInputDialog(
			null, 
			"Enter name of file to load:",
			"No name was given",
			JOptionPane.QUESTION_MESSAGE
			);
		}
		return fileName;
	}

	public void showError(String error){
		JOptionPane.showMessageDialog(
			null, 
			error,
			"Error message",
			JOptionPane.ERROR_MESSAGE
		);
	}
}


