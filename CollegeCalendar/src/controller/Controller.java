package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import database.EventDBO;
import gui.EventGO;

public class Controller {

	private EventDBO edb;

	private final String EVENT_NAME_KEY = "title";
	private ObjectMapper oMapper = new ObjectMapper();



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
			EventType type = EventType.valueOf(doc.getInteger("eventType"));
			String title = doc.getString(EVENT_NAME_KEY);
			LocalDate date = oMapper.convertValue(doc.get("date"), LocalDate.class);
			LocalTime time = oMapper.convertValue(doc.get("time"), LocalTime.class);
			Duration duration = oMapper.convertValue(doc.get("duration"), Duration.class);
			LocalDate endRepeat = oMapper.convertValue(doc.get("endRepeat"), LocalDate.class);
			Duration notificationOffset = oMapper.convertValue(doc.get("notificationOffset"), Duration.class);
			
			int priority = doc.getInteger("priority");
			Boolean[] repeatDays = (Boolean[])doc.get("repeatDays");
			Boolean completed = doc.getBoolean("completed");
			String user = doc.getString("userName");
			String id = oid.toString();
			al.add(new EventGO(type, id, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, user));
		}
		return al;
	}

	public String getEventNameById(EventGO ego) {
		Document d = edb.getEvent(ego.getID());
		String name = (String)d.get(EVENT_NAME_KEY);
		return name;
	}

	// Return ID of data in database
	public String addEventToDatabase(EventGO e) {
		int type = e.getType().ordinal();
		String title = e.getTitle();
		BasicDBObject date = oMapper.convertValue(e.getDate(), BasicDBObject.class);
		BasicDBObject time = oMapper.convertValue(e.getTime(), BasicDBObject.class);
		BasicDBObject duration = oMapper.convertValue(e.getDuration(), BasicDBObject.class);
		BasicDBObject endRepeat = oMapper.convertValue(e.getEndRepeat(), BasicDBObject.class);
		BasicDBObject notificationOffset = oMapper.convertValue(e.getNotificationOffset(), BasicDBObject.class);
		int priority = e.getPriority();
		String repeatDays = e.getRepeatDays().toString();
		Boolean completed = e.getCompleted();
		String userName = e.getUserName();
		return edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
	}

	public void deleteEventFromDatabase(ObjectId id) {
		edb.deleteEvent(id.toString());
	}

	// Return ID of data in database
	public void updateEventInDatabase(EventGO e) {
		ObjectId oid = new ObjectId(e.getID());
		int type = e.getType().ordinal();
		
		BasicDBObject date = oMapper.convertValue(e.getDate(), BasicDBObject.class);
		BasicDBObject time = oMapper.convertValue(e.getTime(), BasicDBObject.class);
		BasicDBObject duration = oMapper.convertValue(e.getDuration(), BasicDBObject.class);
		BasicDBObject endRepeat = oMapper.convertValue(e.getEndRepeat(), BasicDBObject.class);
		BasicDBObject notificationOffset = oMapper.convertValue(e.getNotificationOffset(), BasicDBObject.class);
		String title = e.getTitle();
		int priority = e.getPriority();
		String repeatDays = e.getRepeatDays().toString();
		Boolean completed = e.getCompleted();
		String userName = e.getUserName();
		edb.updateEvent(oid, type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
	}

	public EventGO getEventInDatabase(String id) {
		Document doc = edb.getEvent(id);
		EventType type = EventType.valueOf(doc.getInteger("eventType"));
		String title = doc.getString(EVENT_NAME_KEY);
		LocalDate date = oMapper.convertValue(doc.get("date"), LocalDate.class);
		LocalTime time = oMapper.convertValue(doc.get("time"), LocalTime.class);
		Duration duration = oMapper.convertValue(doc.get("duration"), Duration.class);
		LocalDate endRepeat = oMapper.convertValue(doc.get("endRepeat"), LocalDate.class);
		Duration notificationOffset = oMapper.convertValue(doc.get("notificationOffset"), Duration.class);
		
		int priority = doc.getInteger("priority");
		Boolean[] repeatDays = (Boolean[])doc.get("repeatDays");

		Boolean completed = doc.getBoolean("completed");
		String userName = doc.getString("userName");
		return new EventGO(type, id, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);	
	}


}