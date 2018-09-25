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


	private static final String EVENT_NAME_KEY = "title";


	public Controller() {
		this.edb = new EventDBO();
	}
	
	private static Boolean[] fromString(String string) {
	    String[] strings = string.replace("[", "").replace("]", "").split(", ");
	    Boolean result[] = new Boolean[strings.length];
	    for (int i = 0; i < result.length; i++) {
	      result[i] = Boolean.parseBoolean(strings[i]);
	    }
	    return result;
	  }
	
	public static EventGO convertDocToEventGO(Document doc){
		ObjectId oid = doc.getObjectId("_id");
		EventType type = EventType.valueOf(doc.getInteger("eventType"));
		String title = doc.getString(EVENT_NAME_KEY);
		LocalDate date = LocalDate.parse(doc.getString("date"));
		LocalTime time = LocalTime.parse(doc.getString("time"));
		Duration duration = Duration.parse(doc.getString("duration"));
		int priority = doc.getInteger("priority");
		Boolean[] repeatDays = fromString(doc.getString("repeatDays"));
		LocalDate endRepeat = LocalDate.parse(doc.getString("endRepeat"));
		Duration notificationOffset = Duration.parse(doc.getString("notificationOffset"));
		Boolean completed = doc.getBoolean("completed");
		String user = doc.getString("userName");
		String id = oid.toString();
		return new EventGO(type, id, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, user);
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
			al.add(convertDocToEventGO(doc));
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
		LocalDate date = e.getDate();
		LocalTime time = e.getTime();
		Duration duration = e.getDuration();
		int priority = e.getPriority();
		Boolean[] repeatDays = e.getRepeatDays();
		LocalDate endRepeat = e.getEndRepeat();
		Duration notificationOffset = e.getNotificationOffset();
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
		String title = e.getTitle();
		LocalDate date = e.getDate();
		LocalTime time = e.getTime();
		Duration duration = e.getDuration();
		int priority = e.getPriority();
		Boolean[] repeatDays = e.getRepeatDays();
		LocalDate endRepeat = e.getEndRepeat();
		Duration notificationOffset = e.getNotificationOffset();
		Boolean completed = e.getCompleted();
		String userName = e.getUserName();
		edb.updateEvent(oid, type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
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
		String userName = doc.getString("userName");
		return new EventGO(type, id, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		
	}


}