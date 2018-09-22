package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
			EventType type = (EventType)doc.get("eventType");
			String title = doc.getString(EVENT_NAME_KEY);
			LocalDate date = (LocalDate)doc.get("date");
			LocalTime time = (LocalTime)doc.get("time");
			Duration duration = (Duration)doc.get("duration");
			int priority = doc.getInteger("priority");
			Boolean[] repeatDays = (Boolean[])doc.get("repeatDays");
			LocalDate endRepeat = (LocalDate)doc.get("endRepeat");
			Duration notificationOffset = (Duration)doc.get("notificationOffset");
			Boolean completed = doc.getBoolean("completed");
			String id = oid.toString();
			al.add(new EventGO(type, id, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed));
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
		EventType type = e.getType();
		String title = e.getTitle();
		LocalDate date = e.getDate();
		LocalTime time = e.getTime();
		Duration duration = e.getDuration();
		int priority = e.getPriority();
		Boolean[] repeatDays = e.getRepeatDays();
		LocalDate endRepeat = e.getEndRepeat();
		Duration notificationOffset = e.getNotificationOffset();
		Boolean completed = e.getCompleted();
		return edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed);
	}

	public void deleteEventFromDatabase(ObjectId id) {
		edb.deleteEvent(id.toString());
	}

	// Return ID of data in database
	public void updateEventInDatabase(EventGO e) {
		ObjectId oid = new ObjectId(e.getID());
		EventType type = e.getType();
		String title = e.getTitle();
		LocalDate date = e.getDate();
		LocalTime time = e.getTime();
		Duration duration = e.getDuration();
		int priority = e.getPriority();
		Boolean[] repeatDays = e.getRepeatDays();
		LocalDate endRepeat = e.getEndRepeat();
		Duration notificationOffset = e.getNotificationOffset();
		Boolean completed = e.getCompleted();
		edb.updateEvent(oid, type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed);
	}
	
	public EventGO getEventInDatabase(String id) {
		Document doc = edb.getEvent(id);
		EventType type = (EventType)doc.get("eventType");
		String title = doc.getString(EVENT_NAME_KEY);
		LocalDate date = (LocalDate)doc.get("date");
		LocalTime time = (LocalTime)doc.get("time");
		Duration duration = (Duration)doc.get("duration");
		int priority = doc.getInteger("priority");
		Boolean[] repeatDays = (Boolean[])doc.get("repeatDays");
		LocalDate endRepeat = (LocalDate)doc.get("endRepeat");
		Duration notificationOffset = (Duration)doc.get("notificationOffset");
		Boolean completed = doc.getBoolean("completed");
		return new EventGO(type, id, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed);
		
	}


}