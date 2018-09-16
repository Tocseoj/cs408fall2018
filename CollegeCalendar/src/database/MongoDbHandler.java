package database;


import java.util.List;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;


public class MongoDbHandler {
	public static void main(String[] args) {
		@SuppressWarnings({ "unused", "resource" })
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		System.out.println("we lit");
		
		MongoDatabase dbs = mongoClient.getDatabase("simplease");
		System.out.println("Connect to database successfully");
		System.out.println("Database Name: "+dbs.getName());
		
		List<String> dbNames = mongoClient.getDatabaseNames();
		System.out.println(dbNames);
		
		
		mongoClient.close();
	}
}
