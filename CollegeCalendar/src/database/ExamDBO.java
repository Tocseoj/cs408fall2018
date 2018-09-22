package database;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
/**
 * 
 * @author Gus :)
 *
 */
public class ExamDBO {
	private MongoDatabase database;
	private MongoClient mongoClient;
	/**
	 * Constructor for object to interact with exam events
	 * @param database 
	 */
	public ExamDBO() {
		MongoClientURI uri  = new MongoClientURI("mongodb://tester:tester1@ds135441.mlab.com:35441/408calendar");
		this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase(uri.getDatabase());	}

	/**
	 * Inserts an exam associated with a username in the database
	 * @param className the name of the class to add
	 * @param startDate the starting date of the class
	 * @param endDate the ending date of the class
	 * @param building the building the class is in
	 * @param room the room the class is in
	 * @param teacherName the name of the teacher for the class
	 * @param username the user that is adding the class
	 */
	public String insertExam(String examName, String startDate, String endDate, String building, String room, String teacherName, String username) {
		MongoCollection<Document> collection = database.getCollection("exams");
		ObjectId oid = new ObjectId();
		Document newClass = new Document("examName", examName)
				.append("startDate", startDate)
				.append("endDate", endDate)
				.append("building", building)
				.append("room", room)
				.append("teacherName", teacherName)
				.append("user", username)
				.append("_id", oid);
		collection.insertOne(newClass);
		return oid.toString();
	}
	
	/**
	 * Updates exam defined by id with all the given fields
	 * 
	 * @param name
	 * @param dueDate
	 * @param className
	 * @param priorityLevel
	 * @param username
	 * @param id
	 */
	public void updateExam(String examName, String dueDate, String teacherName, String priorityLevel, String username, ObjectId id) {
		if (examName.equals("") || username.equals("")) {
			System.out.println("invalid arguments");
			return;
		}
		MongoCollection<Document> collection = database.getCollection("exams");
		Document updatedHomework = new Document("className", examName)
				.append("dueDate", dueDate)
				.append("teacherName", teacherName)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		collection.updateOne(eq("_id", id), new Document("$set", updatedHomework));
	}

	/**
	 * Get single exam by ID
	 * @param id
	 * @return
	 */
	public Document getExam(String id) {
		ObjectId oid= new ObjectId(id);
		MongoCollection<Document> collection = database.getCollection("exams");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		return dbObj;
	}

	/**
	 * Get all events of type exam
	 * 
	 * @param user
	 * @return iterator of all events
	 */
	public MongoCursor<Document> getAllExams(String user){
		MongoCollection<Document> collection = database.getCollection("exams");
		Document findQuery = new Document("user", user);
		MongoCursor<Document> dbObj = collection.find(findQuery).iterator();
		return dbObj;
	}

	/**
	 * Delete exam event by ID
	 * 
	 * @param id
	 */
	public void deleteExam(String id) {
		ObjectId oid= new ObjectId(id);
		MongoCollection<Document> collection = database.getCollection("exams");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		collection.deleteOne(dbObj);
	}
}
