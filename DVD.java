public class DVD {
	private String title;		// Title of this DVD
	private String rating;		// Rating of this DVD
	private int runningTime;	// Running time of this DVD in minutes

	public DVD(String dvdTitle, String dvdRating, int dvdRunningTime) 
	{
		title = dvdTitle;
		rating = dvdRating;
		runningTime = dvdRunningTime;
	}
	
	//CAN I DO THIS? ADD THIS COPY CONST???? ((((((((((((((((((:
	public DVD(DVD other){
		this.title = other.title;
		this.rating = other.rating;
		this.runningTime = other.runningTime;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public String getRating() 
	{
		return rating;
	}
	
	public int getRunningTime() 
	{
		return runningTime;	
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public void setRating(String newRating) {
		rating = newRating;
	}

	public void setRunningTime(int newRunningTime) {
		runningTime = newRunningTime;
	}

	public String toString() {
		return title +"/" + rating + "/" + String.valueOf(runningTime)+"\n";
	}
}
