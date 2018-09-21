package database;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MeetingDBO {
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	
	public MeetingDBO() {
		MongoClientURI uri  = new MongoClientURI("mongodb://tester:tester1@ds135441.mlab.com:35441/408calendar");
		this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase(uri.getDatabase());
	}
	
	/*
	 * Main method for DbMeetingHandler
	 */
	public static void main(String[] args) {
		MeetingDBO dbmh = new MeetingDBO();
		
		dbmh.insertMeeting("testMeeting", "9/21/2018", "9:30", "10:30", "Weekly", 10, "testUser");
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
		/*while (ret.hasNext()) {
			System.out.println("in here");
			String curr = ret.next().toJson();
			System.out.println(curr);
		}
		*/
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
	public Document getMeetingById(String s) {
		ObjectId id = new ObjectId(s);
		MongoCollection<Document> collection = database.getCollection("meetings");
		Document meeting = collection.find(eq("_id", id)).first();
		return meeting;
	}
	
	
}
