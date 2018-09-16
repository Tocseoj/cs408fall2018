package database;


import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MongoDbHandler {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	
//	private static MongoCollection<Document> collection;
	
	public MongoDbHandler() {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("408testdb");
//		collection = database.getCollection("testData");
		
	}
	
	
	public static void main(String[] args) {
		
		MongoDbHandler handler = new MongoDbHandler();
		
		handler.insertUser("testUser", "testPassword");
		
//		MongoClient mongoClient = new MongoClient("localhost", 27017);
		
//		DB database = mongoClient.getDB("408testdb");
//		DBCollection collection = database.getCollection("users");
//		System.out.println(collection);
		
		
//		List<String> dbNames = mongoClient.getDatabaseNames();
//		System.out.println(dbNames);
		
		
//		mongoClient.close();
	}
	
	
	void insertUser(String username, String password) {
		MongoCollection<Document> collection = database.getCollection("users");
		Document newUser = new Document("_id", 1)
				.append("username", username)
				.append("password", password);
		
		collection.insertOne(newUser);
	}
	
	
}
