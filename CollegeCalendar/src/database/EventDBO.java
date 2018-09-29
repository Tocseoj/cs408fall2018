package database;

import static com.mongodb.client.model.Filters.eq;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class EventDBO {
	private MongoDatabase database;
	private MongoClient mongoClient;
	
	/**
	 * Constructor for object to interact with event events
	 * @param database 
	 */
	public EventDBO() {
		MongoClientURI uri  = new MongoClientURI("mongodb://tester:tester1@ds135441.mlab.com:35441/408calendar");
		this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase(uri.getDatabase());
	}

	/**
	 * Inserts an event associated with a username in the database
	 * @param className the name of the class to add
	 * @param startDate the starting date of the class
	 * @param endDate the ending date of the class
	 * @param building the building the class is in
	 * @param room the room the class is in
	 * @param teacherName the name of the teacher for the class
	 * @param username the user that is adding the class
	 */
	public String insertEvent(int type, String title, LocalDate date, LocalTime time, 
								Duration duration, int priority, Boolean[] repeatDays, LocalDate endRepeat,
								Duration notificationOffset, Boolean completed, String userName) {
		if(date == null || time == null || duration == null || endRepeat == null || notificationOffset == null) {
			return "";
		}
		MongoCollection<Document> collection = database.getCollection("events");
		ObjectId oid = new ObjectId();
		Document newClass = new Document("eventType", type)
				.append("title", title)
				.append("date", date.toString())
				.append("time", time.toString())
				.append("duration", duration.toString())
				.append("priority", priority)
				.append("repeatDays", Arrays.toString(repeatDays))
				.append("endRepeat", endRepeat.toString())
				.append("notificationOffset", notificationOffset.toString())
				.append("completed", completed)
				.append("_id", oid)
				.append("userName", userName);
		collection.insertOne(newClass);
		return oid.toString();
	}
	
	/**
	 * Updates event defined by id with all the given fields
	 * 
	 * @param name
	 * @param dueDate
	 * @param className
	 * @param priorityLevel
	 * @param username
	 * @param id
	 */
	public void updateEvent(ObjectId id, int type, String title, LocalDate date, LocalTime time, 
							Duration duration, int priority, Boolean[] repeatDays, LocalDate endRepeat,
							Duration notificationOffset, Boolean completed, String userName) {
		if (title.equals("")|| date == null || time == null || duration == null || endRepeat == null || notificationOffset == null) {
			System.out.println("invalid arguments");
			return;
		}

		MongoCollection<Document> collection = database.getCollection("events");
		Document updatedHomework = new Document("eventType", type)
				.append("title", title)
				.append("date", date.toString())
				.append("time", time.toString())
				.append("duration", duration.toString())
				.append("priority", priority)
				.append("repeatDays", Arrays.toString(repeatDays))
				.append("endRepeat", endRepeat.toString())
				.append("notificationOffset", notificationOffset.toString())
				.append("completed", completed);
		collection.updateOne(eq("_id", id), new Document("$set", updatedHomework));
	}

	/**
	 * Get single event by ID
	 * @param id
	 * @return
	 */
	public Document getEvent(String id) {
		ObjectId oid;
		try {
			oid = new ObjectId(id);
		}catch(IllegalArgumentException e) {
			return null;
		}
		MongoCollection<Document> collection = database.getCollection("events");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		return dbObj;
	}

	/**
	 * Get all events of type event
	 * 
	 * @param user
	 * @return iterator of all events
	 */
	public MongoCursor<Document> getAllEvents(String user){
		MongoCollection<Document> collection = database.getCollection("events");
		Document findQuery = new Document("userName", user);
		MongoCursor<Document> dbObj = collection.find(findQuery).iterator();
		return dbObj;
	}

	/**
	 * Delete event event by ID
	 * 
	 * @param id
	 */
	public void deleteEvent(String id) {
		ObjectId oid;
		try {
			oid = new ObjectId(id);
		}catch(IllegalArgumentException e) {
			return;
		}
		MongoCollection<Document> collection = database.getCollection("events");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		if(dbObj != null) {
			collection.deleteOne(dbObj);
		}
	}
}
