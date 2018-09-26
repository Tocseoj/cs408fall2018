package controllerTest;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import controller.Controller;
import controller.EventType;
import database.EventDBO;
import gui.EventGO;

public class Test_Controller {
private Controller c = new Controller();
	
	@Test
	public void testGetAllEvents_Valid() {
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
		String userName = "permanentTester";
		String id = edb.insertEvent(type, title, date, time, duration, priority, repeatDays, endRepeat, notificationOffset, completed, userName);
		
		ArrayList<EventGO> events = c.getAllEvents("permanentTester");
		assertEquals(2, events.size());
		edb.deleteEvent(id);
	}
	
	@Test
	public void testGetAllEvents_Invalid() {
		ArrayList<EventGO> events = c.getAllEvents("invalidtester");
		assertEquals(0, events.size());
	}
	
	@Test
	public void testGetEventNameById() {
		ArrayList<EventGO> events = c.getAllEvents("permanentTester");
		assertEquals("permanentEventDon'tDelete" ,c.getEventNameById(events.get(0)));
	}
	
	@Test
	public void testGetEventNameById_Null() {
		assertEquals("" , c.getEventNameById(null));
	}
	
	@Test
	public void testAddEventToDatabase_Null() {
		assertEquals("", c.addEventToDatabase(null));
	}
	
	@Test
	public void testAddEventAndDeleteEvent() {
		EventGO e = new EventGO("title", "permanentTester");
		c.addEventToDatabase(e);
		assertEquals("title",c.getEventNameById(e));
		c.deleteEventFromDatabase(e.getID());
		assertEquals("",c.getEventNameById(e));
	}
	
	@Test
	public void testDeleteEvent_Null() {
		ArrayList<EventGO> events = c.getAllEvents("permanentTester");
		c.deleteEventFromDatabase("");
		assertEquals(1, events.size());
	}
	
}
