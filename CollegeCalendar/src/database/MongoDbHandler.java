package database;


import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.crypto.Cipher;

/**
 * 
 * @author Abhi Gupta
 *
 */


public class MongoDbHandler {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	
	
	public MongoDbHandler() {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("408testdb");
		
	}
	
	
	/*
	 * Main method for the database handler
	 */
	public static void main(String[] args) {
		
		MongoDbHandler handler = new MongoDbHandler();
		
		handler.insertUser("testUser", "testPassword");
		
		handler.getAllUsers();
	}
	
	
	/*
	 * Inserts a user with the given username and password in the database, under
	 * users collection
	 * 
	 * @param username the username of the new user
	 * @param password the password of the new user
	 */
	void insertUser(String username, String password) {
		MongoCollection<Document> collection = database.getCollection("users");
		Document newUser = new Document("username", username)
				.append("password", password);
		
		collection.insertOne(newUser);
	}
	
	
	/*
	 * Get all the users currently in the database
	 */
	void getAllUsers() {
		MongoCollection<Document> collection = database.getCollection("users");
		collection.find().forEach(printBlock);
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
