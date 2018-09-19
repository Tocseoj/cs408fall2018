package database;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class GenericDBO {
	private MongoDatabase database;

	/**
	 * Constructor for object to interact with event events
	 * @param database 
	 */
	public GenericDBO(MongoDatabase database) {
		this.database = database;
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
	void insertEvent(String className, String startDate, String endDate, String building, String room, String teacherName, String username) {
		MongoCollection<Document> collection = database.getCollection("events");
		Document newClass = new Document("className", className)
				.append("startDate", startDate)
				.append("endDate", endDate)
				.append("building", building)
				.append("room", room)
				.append("teacherName", teacherName)
				.append("user", username);
		collection.insertOne(newClass);
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
	void updateEvent(String name, String dueDate, String className, String priorityLevel, String username, ObjectId id) {
		if (name.equals("") || username.equals("")) {
			System.out.println("invalid arguments");
			return;
		}

		MongoCollection<Document> collection = database.getCollection("events");
		Document updatedHomework = new Document("name", name)
				.append("dueDate", dueDate)
				.append("className", className)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		collection.updateOne(eq("_id", id), new Document("$set", updatedHomework));
	}

	/**
	 * Get single event by ID
	 * @param id
	 * @return
	 */
	Document getEvent(String id) {
		ObjectId oid= new ObjectId(id);
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
	MongoCursor<Document> getAllEvents(String user){
		MongoCollection<Document> collection = database.getCollection("events");
		Document findQuery = new Document("user", user);
		MongoCursor<Document> dbObj = collection.find(findQuery).iterator();
		return dbObj;
	}

	/**
	 * Delete event event by ID
	 * 
	 * @param id
	 */
	void deleteEvent(String id) {
		ObjectId oid= new ObjectId(id);
		MongoCollection<Document> collection = database.getCollection("events");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		collection.deleteOne(dbObj);
	}
}