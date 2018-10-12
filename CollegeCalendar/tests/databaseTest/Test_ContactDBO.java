package databaseTest;

import static org.junit.Assert.assertNotNull;

import org.bson.Document;
import org.junit.Test;

import database.ContactDBO;

public class Test_ContactDBO {
	@Test
	// Testing whether events can be received from the database
	public void getEvent_Exists() {
		ContactDBO cdb = new ContactDBO();
		final String PERM_ID = "5bc11ae6e7179a4377f8057a"; // passing it the ID of the permanent event used for testing
		Document event = cdb.getContact(PERM_ID);
		assertNotNull(event);
	}
}
