package databaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import controller.EventType;
import database.EventDBO;

public class Test_EventDBO {

	private final String INVALID_ID = "5ba7dcdde0fec83c8d000000";
	private ObjectMapper oMapper = new ObjectMapper();

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
		LocalDate tempDate = LocalDate.now();
		LocalTime tempTime = LocalTime.now();
		Duration tempDuration = Duration.ofHours(1);
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate tEndRepeat = tempDate.plusDays(1);
		Duration tNotificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		
		BasicDBObject date = oMapper.convertValue(tempDate, BasicDBObject.class);
		BasicDBObject time = oMapper.convertValue(tempTime, BasicDBObject.class);
		BasicDBObject duration = oMapper.convertValue(tempDuration, BasicDBObject.class);
		BasicDBObject endRepeat = oMapper.convertValue(tEndRepeat, BasicDBObject.class);
		BasicDBObject notificationOffset = oMapper.convertValue(tNotificationOffset, BasicDBObject.class);
		
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
		LocalDate tempDate = LocalDate.now();
		LocalTime tempTime = LocalTime.now();
		Duration tempDuration = Duration.ofHours(1);
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate tEndRepeat = tempDate.plusDays(1);
		Duration tNotificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		
		BasicDBObject date = oMapper.convertValue(tempDate, BasicDBObject.class);
		BasicDBObject time = oMapper.convertValue(tempTime, BasicDBObject.class);
		BasicDBObject duration = oMapper.convertValue(tempDuration, BasicDBObject.class);
		BasicDBObject endRepeat = oMapper.convertValue(tEndRepeat, BasicDBObject.class);
		BasicDBObject notificationOffset = oMapper.convertValue(tNotificationOffset, BasicDBObject.class);
		
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
		assertTrue(events.hasNext());
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
		LocalDate tempDate = LocalDate.now();
		LocalTime tempTime = LocalTime.now();
		Duration tempDuration = Duration.ofHours(1);
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate tEndRepeat = tempDate.plusDays(1);
		Duration tNotificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		
		BasicDBObject date = oMapper.convertValue(tempDate, BasicDBObject.class);
		BasicDBObject time = oMapper.convertValue(tempTime, BasicDBObject.class);
		BasicDBObject duration = oMapper.convertValue(tempDuration, BasicDBObject.class);
		BasicDBObject endRepeat = oMapper.convertValue(tEndRepeat, BasicDBObject.class);
		BasicDBObject notificationOffset = oMapper.convertValue(tNotificationOffset, BasicDBObject.class);
		
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
		ObjectId invalidId = new ObjectId(INVALID_ID);
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate tempDate = LocalDate.now();
		LocalTime tempTime = LocalTime.now();
		Duration tempDuration = Duration.ofHours(1);
		int priority = 2;
		String repeatDays = (new Boolean[8]).toString();
		LocalDate tEndRepeat = tempDate.plusDays(1);
		Duration tNotificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		
		BasicDBObject date = oMapper.convertValue(tempDate, BasicDBObject.class);
		BasicDBObject time = oMapper.convertValue(tempTime, BasicDBObject.class);
		BasicDBObject duration = oMapper.convertValue(tempDuration, BasicDBObject.class);
		BasicDBObject endRepeat = oMapper.convertValue(tEndRepeat, BasicDBObject.class);
		BasicDBObject notificationOffset = oMapper.convertValue(tNotificationOffset, BasicDBObject.class);
		
		String updatedTitle = "updatedEvent";
		
		edb.updateEvent(invalidId, type, updatedTitle, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		Document doc = edb.getEvent(INVALID_ID);
		assertNull(doc);
	}
}