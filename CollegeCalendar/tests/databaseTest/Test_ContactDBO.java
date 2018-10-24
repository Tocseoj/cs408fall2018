package databaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.Document;
import org.junit.Test;

import controller.EventType;
import database.ContactDBO;
import database.EventDBO;

public class Test_ContactDBO {
	@Test
	// Testing whether events can be received from the database
	public void getContact_Exists() {
		ContactDBO cdb = new ContactDBO();
		final String PERM_ID = "5bc11ae6e7179a4377f8057a"; // passing it the ID of the permanent event used for testing
		Document event = cdb.getContact(PERM_ID);
		assertNotNull(event);
	}
	
	@Test
	public void testInsertContact() throws Exception {
		ContactDBO cdb = new ContactDBO();
		String contactName = "Mitch Zimmer";
		String userName = "tester";
		String id = cdb.insertContact(userName, contactName);
		Document insertedEvent = cdb.getContact(id);
		assertNotNull(insertedEvent);
		cdb.deleteContact(id);
	}
	
	@Test
	public void testInsertContact_Exists() throws Exception {
		ContactDBO cdb = new ContactDBO();
		String contactName = "permanentContact";
		String userName = "permanentTester";
		String id = cdb.insertContact(userName, contactName);
		assertEquals("", id);
	}
	
}
