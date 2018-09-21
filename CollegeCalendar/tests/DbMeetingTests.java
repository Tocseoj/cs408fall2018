import database.DbUserHandler;
import database.DbMeetingHandler;
import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;
//import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.MongoDatabase;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class DbMeetingTests {
	
	/*
	 * Tests whether a meeting can be inserted into the database 
	 */
	@Test
	public void testInsertMeeting() throws Exception{
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbMeetingHandler dbmh = new DbMeetingHandler(mongoClient2);
		
		dbmh.insertMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 10, "testing_database_user");
		Document insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
//		System.out.println(insertedMeeting);
		ObjectId id = insertedMeeting.get("_id", ObjectId.class);
//		System.out.println(id);
		dbmh.deleteMeeting(id);
		
		mongoClient2.close();
		
		assertNotNull(insertedMeeting);
		
	}
	
	/*
	 * Tests if the database can successfully delete a meeting
	 */
	@Test
	public void testDeleteMeeting() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbMeetingHandler dbmh = new DbMeetingHandler(mongoClient2);
		
		dbmh.insertMeeting("test", "9/19/2018", "9:30", "10:30", "Weekly", 10, "testing_database_user");
		Document insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
		System.out.println(insertedMeeting);
		ObjectId id = insertedMeeting.get("_id", ObjectId.class);
		System.out.println(id);
		dbmh.deleteMeeting(id);
		
		insertedMeeting = dbmh.getOneMeetingByUsername("testing_database_user");
		
		mongoClient2.close();
		assertNull(insertedMeeting);
	}
	
	/*
	 * Tests if the database can update a meeting
	 */
	@Test
	public void testUpdateMeeting() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbMeetingHandler dbmh = new DbMeetingHandler(mongoClient2);
		
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
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbMeetingHandler dbmh = new DbMeetingHandler(mongoClient2);
		
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
