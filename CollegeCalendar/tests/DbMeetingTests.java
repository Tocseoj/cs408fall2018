//import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.client.MongoCursor;

import database.MeetingDBO;


public class DbMeetingTests {
	
	/*
	 * Tests whether a meeting can be inserted into the database 
	 */
	@Test
	public void testInsertMeeting() throws Exception{
		MeetingDBO dbmh = new MeetingDBO();
		
		dbmh.insertMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 10, "testing_database_user");
		Document insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
//		System.out.println(insertedMeeting);
		ObjectId id = insertedMeeting.get("_id", ObjectId.class);
//		System.out.println(id);
		dbmh.deleteMeeting(id);
				
		assertNotNull(insertedMeeting);
		
	}
	
	/*
	 * Tests if the database can successfully delete a meeting
	 */
	@Test
	public void testDeleteMeeting() throws Exception {
		MeetingDBO dbmh = new MeetingDBO();
		
		dbmh.insertMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 10, "testing_database_user");
		Document insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
		System.out.println(insertedMeeting);
		ObjectId id = insertedMeeting.get("_id", ObjectId.class);
		System.out.println(id);
		dbmh.deleteMeeting(id);
		
		insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
		
		assertNull(insertedMeeting);
	}
	
	/*
	 * Tests if the database can update a meeting
	 */
	@Test
	public void testUpdateMeeting() throws Exception {
		MeetingDBO dbmh = new MeetingDBO();
		
		dbmh.insertMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 10, "testing_database_user");
		
		
		// get the document from the db, parse it to get its id
		Document insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
//		System.out.println(insertedMeeting);
		ObjectId id = insertedMeeting.get("_id", ObjectId.class);
//		System.out.println(id);
		
		// run an update, using the id
		dbmh.updateMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 8, "testing_database_user", id);
		
		// get the updated document
		insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
		int newPriority = insertedMeeting.get("priorityLevel", Integer.class);
		
		boolean isChangedPriority;
		
		if (newPriority != 10) {
			isChangedPriority = true;
		}
		else isChangedPriority = false;
		
		dbmh.deleteMeeting(id);
		
		
		assertTrue(isChangedPriority);
	}
	
	
	/*
	 * 
	 */
	@Test
	public void testGetMeetingsByUsername() {
		MeetingDBO dbmh = new MeetingDBO();
		
		dbmh.insertMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 10, "testing_database_user");
		
		
		// get the document from the db, parse it to get its id
		MongoCursor<Document> insertedMeetings = dbmh.getMeetingsByUsername("testing_database_user");
//		System.out.println(insertedMeeting);
//		ObjectId id = insertedMeeting.get("_id", ObjectId.class);
//		System.out.println(id);
		
		dbmh.deleteMeetingsByUsername("testing_database_user");
		
		assertNotNull(insertedMeetings);
	}
	
}
