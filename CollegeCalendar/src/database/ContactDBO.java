package database;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ContactDBO {
	private MongoDatabase database;
	private MongoClient mongoClient;

	/**
	 * Constructor for object to interact with event contacts
	 * @param database 
	 */
	public ContactDBO() {
		MongoClientURI uri  = new MongoClientURI("mongodb://tester:tester1@ds135441.mlab.com:35441/408calendar");
		this.mongoClient = new MongoClient(uri);
		this.database = mongoClient.getDatabase(uri.getDatabase());
	}

	public Document getContact(String id) {
		ObjectId oid;
		try {
			oid = new ObjectId(id);
		}catch(IllegalArgumentException e) {
			return null;
		}
		MongoCollection<Document> collection = database.getCollection("contacts");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		return dbObj;
	}

	public String insertContact(String userName, String contactName) {
		if(userName == null || contactName == null) {
			return "";
		}
		MongoCursor<Document> mc = getAllContacts(userName);
		Document doc;
		if(mc!= null) {
			while(mc.hasNext()) {
				doc = mc.next();
				if(doc.getString("contactName").equals(contactName)) {
					return "";
				}
			}
		}
		
		MongoCollection<Document> collection = database.getCollection("contacts");
		ObjectId oid = new ObjectId();
		Document newClass = new Document("userName", userName)
				.append("contactName", contactName)
				.append("_id", oid);
		collection.insertOne(newClass);
		return oid.toString();
	}

	public void updateContact(ObjectId id, String userName, String contactName) {
		if (userName==null || contactName == null) {
			System.out.println("invalid arguments");
			return;
		}
		MongoCollection<Document> collection = database.getCollection("contacts");
		Document updatedHomework = new Document("userName",userName)
				.append("contactName", contactName);
		collection.updateOne(eq("_id", id), new Document("$set", updatedHomework));
	}
	

	public MongoCursor<Document> getAllContacts(String user){
		MongoCollection<Document> collection = database.getCollection("contacts");
		Document findQuery = new Document("userName", user);
		MongoCursor<Document> dbObj = collection.find(findQuery).iterator();
		return dbObj;
	}
	
	public void deleteContact(String id) {
		ObjectId oid;
		try {
			oid = new ObjectId(id);
		}catch(IllegalArgumentException e) {
			return;
		}
		MongoCollection<Document> collection = database.getCollection("contacts");
		Document findQuery = new Document("_id", oid);
		Document dbObj = collection.find(findQuery).first();
		if(dbObj != null) {
			collection.deleteOne(dbObj);
		}
		else {
			System.out.println("invalid id for deletion");
		}
	}
}
