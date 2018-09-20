package database;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DbMeetingHandler {
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	
	
	public DbMeetingHandler(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
		database = mongoClient.getDatabase("408testdb");
	}
	
	
	
	/*
	 * Main method for DbMeetingHandler
	 */
	public static void main(String[] args) {
		MongoClient mongoClient2 = new MongoClient("localhost", 27017);
		DbMeetingHandler dbmh = new DbMeetingHandler(mongoClient2);
		
		ObjectId oid = new ObjectId("5ba1916518f6315f7ca7cd6b");
		
		dbmh.deleteMeeting(oid);
		dbmh.getMeetingsByUsername("testUser");
	}
	
	
	/*
	 * Inserts a new meeting associated with a username in the database
	 * @param meetingName the name of the meeting
	 * @param date the date of the meeting
	 * @param startTime the time the meeting starts
	 * @param endTime	the time the meeting ends
	 * @param username	the user adding the meeting
	 */
	public void insertMeeting(String meetingName, String date, String startTime, String endTime, String repeats, int priorityLevel, String username) {
		MongoCollection<Document> collection = database.getCollection("meetings");
		Document newMeeting = new Document("meetingName", meetingName)
				.append("date", date)
				.append("startTime", startTime)
				.append("endTime", endTime)
				.append("repeats", repeats)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		collection.insertOne(newMeeting);
	}
	
	/*
	 * Updates an existing meeting with the given fields.
	 */
	public void updateMeeting(String meetingName, String date, String startTime, String endTime, String repeats, int priorityLevel, String username,
			ObjectId id) {
		
		if (meetingName.equals("") || username.equals("")) {
			System.out.println("invalid arguments");
			return;
		}
		
		if (!repeats.equals("Daily") && !repeats.equals("Weekly") && !repeats.equals("Monthly")) {
			System.out.println("invalid value for repeats");
			return;
		}
		
		MongoCollection<Document> collection = database.getCollection("meetings");
		Document updatedMeeting = new Document("name", meetingName)
				.append("startTime", startTime)
				.append("endTime", endTime)
				.append("meetingName", meetingName)
				.append("repeats", repeats)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		collection.updateOne(eq("_id", id), new Document("$set", updatedMeeting));
	}
	
	
	/*
	 * Deletes the meeting specified by the given id
	 * @param id	
	 */
	public void deleteMeeting(ObjectId id) {
		MongoCollection<Document> collection = database.getCollection("meetings");
		collection.deleteOne(eq("_id", id));
	}
	
	
	/*
	 * Deletes meetings associated with a specific username
	 */
	public void deleteMeetingByUsername(String username) {
		MongoCollection<Document> collection = database.getCollection("meetings");
		collection.deleteMany(eq("user", username));
	}
	
	/*
	 * Given a username (normally the one that is logged in) gets all the meetings associated with that user.
	 */
	public MongoCursor<Document> getMeetingsByUsername(String username) {
		MongoCollection<Document> collection = database.getCollection("meetings");
		MongoCursor<Document> ret = collection.find(eq("user", username)).iterator();
		while (ret.hasNext()) {
//			System.out.println("in here");
			String curr = ret.next().toJson();
//			System.out.println(curr);
		}
		return ret;
	}
	
	/*
	 * Given a username, get one meeting associated with the username
	 */
	public Document getOneMeetingByUsername(String username) {
		MongoCollection<Document> collection = database.getCollection("meetings");
		Document meeting = collection.find(eq("user", username)).first();
		
		return meeting;
	}
	
	/*
	 * Queries the database to get a homework by id
	 */
	Document getMeetingById(ObjectId id) {
		MongoCollection<Document> collection = database.getCollection("meetings");
		Document meeting = collection.find(eq("_id", id)).first();
		
		return meeting;
	}
	
	
}
