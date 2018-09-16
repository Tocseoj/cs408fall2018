package database;


import com.mongodb.*;


public class MongoDbHandler {
	public static void main(String[] args) {
		@SuppressWarnings({ "unused", "resource" })
		MongoClient mongoclient = new MongoClient("localhost", 27017);
		System.out.println("we lit");
	}
}
