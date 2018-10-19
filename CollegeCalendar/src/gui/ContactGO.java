package gui;

public class ContactGO {
	private String userName;
	private String contactName;
	private String id;
	public ContactGO(String id, String userName, String contactName) {
		this.id = id;
		this.userName = userName;
		this.contactName = contactName;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
}
