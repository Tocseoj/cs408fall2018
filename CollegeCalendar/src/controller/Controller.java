package controller;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCursor;

import database.EventDBO;
import gui.EventGO;

public class Controller {
	
	private EventDBO edb;
	
	
	private final String EVENT_NAME_KEY = "title";

	
	public Controller() {
		this.edb = new EventDBO();
	}
	
	public ArrayList<EventGO> getAllEvents(String userName){
		MongoCursor<Document> c = edb.getAllEvents(userName);
		ArrayList<EventGO> al = new ArrayList<EventGO>();
		Document doc;
		if(c == null) {
			return al;
		}
		while(c.hasNext()) {
			doc  = c.next();
			ObjectId oid = (ObjectId)doc.get("_id");
			String id = oid.toString();
			String title = doc.getString(EVENT_NAME_KEY);
			al.add(new EventGO(id, title));
		}
		return al;
	}
	
	public String getEventNameById(EventGO ego) {
		Document d = edb.getEvent(ego.getID());
		String name = (String)d.get(EVENT_NAME_KEY);
		return name;
	}
	
	// Adding Events - Joe
	
	// Return ID of data in database
	public String addEventToDatabase(EventGO event) {
		
		// TODO : Add event to database
		
		return "0";
	}
	
	// End Joe

}
