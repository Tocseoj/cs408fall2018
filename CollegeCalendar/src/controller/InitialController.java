package controller;

import org.bson.Document;

import database.MeetingDBO;
import gui.MeetingGO;

public class InitialController {
	
	private MeetingDBO mdb;
	
	public InitialController() {
		this.mdb = new MeetingDBO();
	}
	
	public String getEventDetails(MeetingGO mgo) {
		Document d = mdb.getMeetingById(mgo.getID());
		String name = (String)d.get("meetingName");
		return name;
	}
}
