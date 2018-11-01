package controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		ArrayList<EventGO> events = c.getAllEvents("permanentTester");
		assertEquals(16, events.size());
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
	public void testDeleteEvent_DNE() {
		ArrayList<EventGO> events = c.getAllEvents("permanentTester");
		c.deleteEventFromDatabase("");
		assertEquals(16, events.size());
	}
	
	@Test
	public void testDeleteEvent_InvalidArg() {
		ArrayList<EventGO> events = c.getAllEvents("permanentTester");
		c.deleteEventFromDatabase("5ba7dcdde0fec83c8d000000");
		assertEquals(16, events.size());
	}
	
	@Test
	public void testGetEvent_Null() {
		assertNull(c.getEventInDatabase(""));
	}
	
	@Test
	public void testGetEvent_DNE() {
		assertNull(c.getEventInDatabase("5ba7dcdde0fec83c8d000000"));
	}
	
	@Test
	public void testGetEvent_Success() {
		assertNotNull(c.getEventInDatabase("5bae78c918f6315a8444ee24"));
	}
	
	@Test
	public void testUpdateEvent_Null() {
		c.updateEventInDatabase(null);
	}
	
	
}
