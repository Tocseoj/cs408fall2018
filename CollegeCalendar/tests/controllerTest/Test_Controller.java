package controllerTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import controller.Controller;
import gui.EventGO;

public class Test_Controller {
	
	private Controller c = new Controller();
	
	@Test
	public void testGetAllEvents_Valid() {
		ArrayList<EventGO> events = c.getAllEvents("tester");
		assertEquals(1, events.size());
	}
	
	@Test
	public void testGetAllEvents_Invalid() {
		ArrayList<EventGO> events = c.getAllEvents("invalidtester");
		assertEquals(0, events.size());
	}
}
