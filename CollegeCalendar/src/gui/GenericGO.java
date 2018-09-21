package gui;

public class GenericGO {
	private String id;
	private String title;
	
	public GenericGO(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public String getID() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
}
