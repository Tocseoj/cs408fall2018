package database;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

//import org

public class HomeworkDBO {
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	
	public HomeworkDBO() {
		MongoClientURI uri  = new MongoClientURI("mongodb://tester:tester1@ds135441.mlab.com:35441/408calendar");
		this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase(uri.getDatabase());
	}
	
	public static void main(String[] args) {
		HomeworkDBO dbhh = new HomeworkDBO();
		
//		dbhh.insertHomework("test", "9/19/2018", "CS408", "10", "testUser");
		
//		dbhh.getHomeworkByUsername("testUser");
		ObjectId oid = new ObjectId("5ba1864d18f6312470a1c248");
//		dbhh.updateHomework("test", "9/19/2018", "CS408", "10", "testUser", oid);
		dbhh.deleteHomework(oid);
	}
	
	/*
	 * Inserts an event into the database, using the given username as a field in the db to link the event to its owner
	 * 
	 * @param repeats	either Daily, Weekly, or Monthly
	 */
	void insertHomework(String name, String dueDate, String repeats, String status, String className, int priorityLevel, String username) {
		if (name.equals("") || username.equals("")) {
			System.out.println("invalid arguments");
			return;
		}
		
		if (!repeats.equals("Daily") && !repeats.equals("Weekly") && !repeats.equals("Monthly")) {
			System.out.println("invalid value for repeats");
			return;
		}
		
		MongoCollection<Document> collection = database.getCollection("homeworks");
		Document newHomework = new Document("name", name)
				.append("dueDate", dueDate)
				.append("repeats", repeats)
				.append("status", status)
				.append("className", className)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		
		collection.insertOne(newHomework);
		
	}
	
	/*
	 * Updates an existing homework with the given fields.
	 */
	void updateHomework(String name, String dueDate, String className, String repeats, String status, String priorityLevel, String username, ObjectId id) {
		if (name.equals("") || username.equals("")) {
			System.out.println("invalid arguments");
			return;
		}
		
		if (!repeats.equals("Daily") && !repeats.equals("Weekly") && !repeats.equals("Monthly")) {
			System.out.println("invalid value for repeats");
			return;
		}
		
		MongoCollection<Document> collection = database.getCollection("homeworks");
		Document updatedHomework = new Document("name", name)
				.append("dueDate", dueDate)
				.append("className", className)
				.append("repeats", repeats)
				.append("status", status)
				.append("priorityLevel", priorityLevel)
				.append("user", username);
		collection.updateOne(eq("_id", id), new Document("$set", updatedHomework));
	}
	
	/*
	 * Deletes the homework specified by the given id
	 * @param id	
	 */
	void deleteHomework(ObjectId id) {
		MongoCollection<Document> collection = database.getCollection("homeworks");
		collection.deleteOne(eq("_id", id));
	}
	
	/*
	 * Given a username (normally the one that is logged in) 
	 */
	MongoCursor<Document> getHomeworkByUsername(String username) {
		MongoCollection<Document> collection = database.getCollection("homeworks");
		MongoCursor<Document> ret = collection.find(eq("user", username)).iterator();
		while (ret.hasNext()) {
			System.out.println("in here");
			String curr = ret.next().toJson();
			System.out.println(curr);
		}
		return ret;
	}
	
	/*
	 * Queries the database to get a user by user name
	 */
	Document getHomeworkById(ObjectId id) {
		MongoCollection<Document> collection = database.getCollection("homeworks");
		Document homework = collection.find(eq("_id", id)).first();
		
		return homework;
	}
	
	/*
	 * Used for printing returns from a collection
	 */
	Block<Document> printBlock = new Block<Document>() {
	       @Override
	       public void apply(final Document document) {
	           System.out.println(document.toJson());
	       }
	};
	
}
