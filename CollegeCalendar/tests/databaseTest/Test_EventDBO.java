package databaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.client.MongoCursor;

import controller.EventType;
import database.EventDBO;

public class Test_EventDBO {

	private final String INVALID_ID = "5ba7dcdde0fec83c8d000000";
	
	@Test
	public void getEvent_Exists() {
		EventDBO edb = new EventDBO();
		final String PERM_ID = "5ba7dcdde0fec83c8d604cb9";
		Document event = edb.getEvent(PERM_ID);
		assertNotNull(event);
	}
	
	@Test
	public void getEvent_NonExistent() {
		EventDBO edb = new EventDBO();
		Document event = edb.getEvent(INVALID_ID);
		assertNull(event);
	}
	
	@Test
	public void testInsertEvent() throws Exception {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String duration = Duration.ofHours(1).toString();
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate endRepeat = date.plusDays(1);
		String notificationOffset = Duration.ofMinutes(15).toString();
		boolean completed = false;
		String userName = "tester";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		Document insertedEvent = edb.getEvent(id);
		assertNotNull(insertedEvent);
		edb.deleteEvent(id);
	}

	@Test
	public void testDeleteEvent_Exists() {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String duration = Duration.ofHours(1).toString();
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate endRepeat = date.plusDays(1);
		String notificationOffset = Duration.ofMinutes(15).toString();
		boolean completed = false;
		String userName = "tester";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		edb.deleteEvent(id);
		Document insertedEvent = edb.getEvent(id);
		assertNull(insertedEvent);
	}
	
	@Test
	public void testDeleteEvent_NonExistent() {
		EventDBO edb = new EventDBO();
		final String INVALID_ID = "5ba7dcdde0fec83c8d000000";
		edb.deleteEvent(INVALID_ID);
	}
	
	@Test
	public void testGetAllEvents_ValidUser() {
		EventDBO edb = new EventDBO();
		MongoCursor<Document> events = edb.getAllEvents("tester");
		assertNotNull(events);
	}
	
	@Test
	public void testGetAllEvents_InvalidUser() {
		EventDBO edb = new EventDBO();
		MongoCursor<Document> events = edb.getAllEvents("invalid_tester");
		assertFalse(events.hasNext());
	}
	@Test
	public void updateEvent_Exists() {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String duration = Duration.ofHours(1).toString();
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate endRepeat = date.plusDays(1);
		String notificationOffset = Duration.ofMinutes(15).toString();
		boolean completed = false;
		String userName = "tester";
		String sid = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		ObjectId oid = new ObjectId(sid);
		String updatedTitle = "updatedEvent";
		edb.updateEvent(oid, type, updatedTitle, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		Document doc = edb.getEvent(sid);
		assertEquals("updatedEvent", doc.getString("title"));
	}
	
	@Test
	public void updateEvent_NonExistent() {
		EventDBO edb = new EventDBO();
		ObjectId invalidId = new ObjectId(INVALID_ID);int type = EventType.GENERIC.ordinal();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String duration = Duration.ofHours(1).toString();
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate endRepeat = date.plusDays(1);
		String notificationOffset = Duration.ofMinutes(15).toString();
		boolean completed = false;
		String userName = "tester";
		
		String updatedTitle = "updatedEvent";
		
		edb.updateEvent(invalidId, type, updatedTitle, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		Document doc = edb.getEvent(INVALID_ID);
		assertNull(doc);
	}
}