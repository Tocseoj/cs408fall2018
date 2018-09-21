package controller;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCursor;

import database.MeetingDBO;
import gui.MeetingGO;

public class InitialController {
	
	private MeetingDBO mdb;
	
	public InitialController() {
		this.mdb = new MeetingDBO();
	}
	
	public ArrayList<MeetingGO> getAllMeetings(String userName){
		MongoCursor<Document> c = mdb.getMeetingsByUsername(userName);
		ArrayList<MeetingGO> al = new ArrayList<MeetingGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString("meetingName");
			al.add(new MeetingGO(id, title));
		}
		
		return al;
	}
	
	public String getMeetingName(MeetingGO mgo) {
		Document d = mdb.getMeetingById(mgo.getID());
		String name = (String)d.get("meetingName");
		return name;
	}
}
