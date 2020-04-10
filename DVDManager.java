import java.io.IOException; 

public class DVDManager {
	public static void main(String[] args) {
		DVDCollection dl = new DVDCollection();

		DVDGUI program = new DVDGUI();
		String fileName = program.getFileName();
		String possibleError;
		try {
			possibleError = dl.loadData(fileName);
			// If no errors happened
			if(possibleError.equals("NA")){
				program.launch(dl);
			}
			else{
				program.showError(possibleError);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
