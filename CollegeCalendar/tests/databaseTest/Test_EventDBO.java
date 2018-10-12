package databaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.client.MongoCursor;

import controller.Controller;
import controller.EventType;
import database.EventDBO;
import gui.EventGO;

public class Test_EventDBO {

	private final String INVALID_ID = "5ba7dcdde0fec83c8d000000";

	@Test
	// Testing whether events can be received from the database
	public void getEvent_Exists() {
		EventDBO edb = new EventDBO();
		final String PERM_ID = "5bae7a3918f63166c8c4f490"; // passing it the ID of the permanent event used for testing
		Document event = edb.getEvent(PERM_ID);
		assertNotNull(event);
	}

	@Test
	// Testing whether an invalid event id leads to a null element
	public void getEvent_NonExistent() {
		EventDBO edb = new EventDBO();
		Document event = edb.getEvent(INVALID_ID);
		assertNull(event);
	}
	
	@Test
	// Testing whether an invalid argument leads to a null event object
	public void getEvent_InvalidArg() {
		EventDBO edb = new EventDBO();
		Document event = edb.getEvent("");
		assertNull(event);
	}

	@Test
	// 
	public void getEvent_ActualValuesStoredCorrectly() {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String profName = "";
		String meetingName = "";
		String subjectName = "";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				profName, subjectName, meetingName);

		Document doc = edb.getEvent(id);
		EventGO ego = Controller.convertDocToEventGO(doc);

		assertEquals(EventType.valueOf(type), ego.getType());
		assertEquals(title, ego.getTitle());
		assertEquals(date, ego.getDate());
		assertEquals(time, ego.getTime());
		assertEquals(duration, ego.getDuration());
		assertEquals(priority, ego.getPriority());
		assertEquals(false, ego.getRepeatDays()[0]);
		assertEquals(endRepeat, ego.getEndRepeat());
		assertEquals(notificationOffset, ego.getNotificationOffset());
		assertEquals(completed, ego.getCompleted());
		assertEquals(userName, ego.getUserName());	

		edb.deleteEvent(id);
	}
	
	
	/*@Test
	// Testing whether the permanent event test is able to be inserted
	public void testInsertPermamnentEvent() {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "permanentEventDon'tDelete";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "permanentTester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName, allottedTimeUp, constantReminder);
		assertNotNull(id);
	}
	*/
	@Test
	public void testInsertEvent_Failure() {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		
		String id = edb.insertEvent(type, title, null, time, duration, priority, repeatDays, endRepeat,
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				"", "", "");
		assertEquals("", id);
		
	}
	@Test
	public void testInsertEvent() throws Exception {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				"","","");
		Document insertedEvent = edb.getEvent(id);
		assertNotNull(insertedEvent);
		edb.deleteEvent(id);
	}
	
	
	
	/*
	 * Tests inserting a Class event into the database
	 */
	@Test
	public void testInsertClass() throws Exception {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String profName = "Zhang";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				profName,"","");
		Document insertedEvent = edb.getEvent(id);
		assertNotNull(insertedEvent);
		edb.deleteEvent(id);
	}
	
	/*
	 * Tests inserting a Class event into the database
	 */
	@Test
	public void testInsertExam() throws Exception {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String subject = "CS408";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				"",subject,"");
		Document insertedEvent = edb.getEvent(id);
		assertNotNull(insertedEvent);
		edb.deleteEvent(id);
	}
	
	/*
	 * Tests inserting a Class event into the database
	 */
	@Test
	public void testInsertMeeting() throws Exception {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String meetingPersonName = "Zhang";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				"","",meetingPersonName);
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
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				"","","");
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
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String sid = edb.insertEvent(type, title, date, time, duration, priority,
				repeatDays, endRepeat, notificationOffset, completed, userName, 
				allottedTimeUp, constantReminder, "", "", "");
		ObjectId oid = new ObjectId(sid);
		String updatedTitle = "updatedEvent";
		edb.updateEvent(oid, type, updatedTitle, date, time, duration, priority, repeatDays, 
				endRepeat, notificationOffset, completed, userName, allottedTimeUp,
				constantReminder, "", "", "");
		Document doc = edb.getEvent(sid);
		edb.deleteEvent(sid);
		assertEquals("updatedEvent", doc.getString("title"));
	}

	@Test
	public void updateEvent_NonExistent() {
		EventDBO edb = new EventDBO();
		ObjectId invalidId = new ObjectId(INVALID_ID);
		int type = EventType.GENERIC.ordinal();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;

		String updatedTitle = "updatedEvent";

		edb.updateEvent(invalidId, type, updatedTitle, date, time, duration, priority, repeatDays, 
				endRepeat, notificationOffset, completed, userName, allottedTimeUp, constantReminder, "", "", "");
		Document doc = edb.getEvent(INVALID_ID);
		assertNull(doc);
	}
	

	/*
	 * Integration test for getting events, mocks the whole process of getting events.
	 * GUI makes call to EventGO -> EventGO makes call to EventDBO -> EventDBO interfaces with DB ->
	 * Returns information to GUI
	 */
	@Test
	public void testGetAllEvents() throws Exception{
		Controller controller = new Controller();
		ArrayList<EventGO> all_events = controller.getAllEvents("permanentTester"); // using the permanentTEster to ensure something is received
		assertNotNull(all_events);
	}
	
	
	/*
	 * Integration test for deleting events, also involves a test for adding, since we need to add a temporary
	 * event to have something to remove.
	 */
	@Test
	public void testDeleteEvent() throws Exception {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		String userName = "tester";
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, 
				notificationOffset, completed, userName, allottedTimeUp, constantReminder,
				"","","");
		edb.deleteEvent(id);
		Document insertedEvent = edb.getEvent(id);
		assertNull(insertedEvent);
		
	}
	
	@Test
	public void updateEvent_InvalidInputs() {
		EventDBO edb = new EventDBO();
		int type = EventType.GENERIC.ordinal();
		String title = "event";
		LocalDate date = LocalDate.now();
		LocalTime time = null;
		Duration duration = Duration.ofHours(1);
		int priority = 2;
		Boolean[] repeatDays = new Boolean[8];
		LocalDate endRepeat = date.plusDays(1);
		Duration notificationOffset = Duration.ofMinutes(15);
		boolean completed = false;
		boolean allottedTimeUp = false;
		boolean constantReminder = false;
		String userName = "tester";
		String updatedTitle = "updatedEvent";
		ObjectId oid = new ObjectId("5bae7a3918f63166c8c4f490");
		edb.updateEvent(oid, type, updatedTitle, date, time, duration, priority, repeatDays, 
				endRepeat, notificationOffset, completed, userName, allottedTimeUp, 
				constantReminder, "", "", "");
		Document doc = edb.getEvent("5bae7a3918f63166c8c4f490");
		assertEquals("permanentEventDon'tDelete", doc.getString("title"));
	}
}