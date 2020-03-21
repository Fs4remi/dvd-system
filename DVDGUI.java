import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUI implements DVDUserInterface { 
	private DVDCollection dvdlist;
	private ArrayList <String> valid_ratings;
	private JFrame frame;
	private Font myFont;
	
	public DVDGUI(DVDCollection dl){
		dvdlist = dl;
		valid_ratings= new ArrayList<String>();
		valid_ratings.add("PG");
		valid_ratings.add("G");
		valid_ratings.add("PG-13");
		valid_ratings.add("R");
		valid_ratings.add("NC-17");

		frame = new JFrame("Fatemeh's DVD Collection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(255, 0, 0, 100));
		frame.setLocationRelativeTo(null);
		UIManager.put("OptionPane.minimumSize",new Dimension(550, 250));

		myFont = new Font("Monospaced Plain", Font.BOLD, 25);
	}

	public void processCommands(){
		String[] commands = {"Add/Modify DVD",
			"Remove DVD",
			"Get DVDs By Rating",
			"Get Total Running Time",
			"Save"};
		
		int choice;

		ImageIcon icon = new ImageIcon("images/stub.png");
				
		do {
			choice = JOptionPane.showOptionDialog(frame,
			"Select a command", 
			"DVD Collection", 
			JOptionPane.YES_NO_CANCEL_OPTION, 
			JOptionPane.QUESTION_MESSAGE, 
			icon, 
			commands,
			commands[commands.length - 1]
			);
		
			switch (choice) {
			case -1: checkModifications(); break;
			case 0: doAddOrModifyDVD(); break;
			case 1: doRemoveDVD(); break;
			case 2: doGetDVDsByRating(); break;
			case 3: doGetTotalRunningTime(); break;
			case 4: doSave(); break;
			default:  // do nothing
			}
			
		} while (choice != commands.length -1 );
		System.exit(0);
	}

	private void checkModifications(){
		if(dvdlist.changed()){
			showError("There are unsaved changes");
		}
		else{
			System.exit(0);
		}
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
			frame, 
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
				frame,
				panel,
				frameTitle,
				JOptionPane.QUESTION_MESSAGE
			);
		}
		title = title.toUpperCase();
		
		// Request the rating
		label.setText("Enter rating for " + title);
		String rating = JOptionPane.showInputDialog(
			frame,
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
			frame,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE 
		);
		}
		rating = rating.toUpperCase();
		
		// Request the running time
		label.setText("Enter running time for " + title);
		String time = JOptionPane.showInputDialog(
			frame,
			panel,
			JOptionPane.QUESTION_MESSAGE
		);
		if (time == null) {
			return;
		}
		label.setText("Enter running time for " + title + " using numbers");
		while(! time.matches("^\\d+$") ){ //(.)*(\\d)*(.)
			time = JOptionPane.showInputDialog(
			frame,
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
			frame,
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
				frame,
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
			frame,
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
			frame,
			panel,
			frameTitle,
			JOptionPane.QUESTION_MESSAGE
			);
		}
		rating = rating.toUpperCase();
		
		String results = dvdlist.getDVDsByRating(rating);
		
		String [] separatedResults = results.split("/");

		if(separatedResults.length > 0) {
			JOptionPane.showMessageDialog(
			frame,
			results,
			frameTitle,
			JOptionPane.INFORMATION_MESSAGE
			);
		}
		else{
			JOptionPane.showMessageDialog(
			frame,
			"There are no movies with " + rating + " rating",
			frameTitle,
			JOptionPane.INFORMATION_MESSAGE
			);
		}
	}

    private void doGetTotalRunningTime() {
		int total = dvdlist.getTotalRunningTime();
		if(total > 0){
			JOptionPane.showMessageDialog(
				frame, 
				total,
				"Sum of all DVD's running time",
				1
			);
		}
		else{
			JOptionPane.showMessageDialog(
				frame, 
				"There are no DVDs saved right now.",
				"Sum of all DVD's running time",
				1
			);
		}
		
	}

	private void doSave() {
		dvdlist.save();
		JOptionPane.showMessageDialog(
				frame, 
				"Changes saved to collection",
				"Success message",
				1
		);
	}
	
	public String getFileName(){
		JPanel panel = new JPanel();
        panel.setBackground(new Color(235, 190, 47, 225));
        panel.setLayout(null);

        JLabel label = new JLabel("Enter name of file");
        label.setForeground(new Color(121, 16, 202));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(myFont);
        label.setBounds(0, 0, 400, 100);
		panel.add(label);
		
		String fileName;
		fileName = JOptionPane.showInputDialog(
			frame,
			panel,
			"Collection name",
			JOptionPane.QUESTION_MESSAGE
		);

		if(fileName == null)
			System.exit(0);
		
		while(fileName.length() == 0){
			fileName = JOptionPane.showInputDialog(
			frame, 
			panel, //"Enter name of file to load:",
			"No name was given",
			JOptionPane.QUESTION_MESSAGE
			);
		}
		
		return fileName;
	}

	public void showError(String error){
		JPanel panel = new JPanel();
        panel.setBackground(new Color(225, 65, 62, 225));
        panel.setLayout(null);

        JLabel label = new JLabel(error);
        label.setForeground(new Color(55, 74, 93));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(myFont);
        label.setBounds(0, 0, 400, 100);
		panel.add(label);

		JOptionPane.showMessageDialog(
			frame, 
			panel,
			"Error message",
			JOptionPane.ERROR_MESSAGE
		);
	}
}


