package database;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ClassDBO {

	private MongoDatabase database;

	/**
	 * Constructor for object to interact with class eventss
	 * @param database 
	 */
	public ClassDBO(MongoDatabase database) {
		this.database = database;
	}

	/**
	 * Inserts document into the class collection
	 * @param className
	 * @param startDate
	 * @param endDate
	 * @param building
	 * @param room
	 * @param teacherName
	 * @param username
	 */
	void insertClassEvent(String className, String startDate, String endDate, String building, String room, String teacherName, String username) {
		MongoCollection<Document> collection = database.getCollection("classes");
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
	 * Updates class defined by id with all the given fields
	 * 
	 * @param name
	 * @param dueDate
	 * @param className
	 * @param priorityLevel
	 * @param username
	 * @param id
	 */
	void updateClassEvent(String name, String dueDate, String className, String priorityLevel, String username, ObjectId id) {
		if (name.equals("") || username.equals("")) {
			System.out.println("invalid arguments");
			return;
		}

		MongoCollection<Document> collection = database.getCollection("classes");
		Document updatedHomework = new Document("name", name)
				.append("dueDate", dueDate)
				.append("className", className)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		collection.updateOne(eq("_id", id), new Document("$set", updatedHomework));
	}

	/**
	 * Get single class by ID
	 * @param id
	 * @return
	 */
	Document getClassEvent(String id) {
		ObjectId oid= new ObjectId(id);
		MongoCollection<Document> collection = database.getCollection("classes");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		return dbObj;
	}

	/**
	 * Get all events of type class
	 * 
	 * @param user
	 * @return iterator of all events
	 */
	MongoCursor<Document> getAllClasses(String user){
		MongoCollection<Document> collection = database.getCollection("classes");
		Document findQuery = new Document("user", user);
		MongoCursor<Document> dbObj = collection.find(findQuery).iterator();
		return dbObj;
	}

	/**
	 * Delete class event by ID
	 * 
	 * @param id
	 */
	void deleteClassEvent(String id) {
		ObjectId oid= new ObjectId(id);
		MongoCollection<Document> collection = database.getCollection("classes");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		collection.deleteOne(dbObj);
	}

}
