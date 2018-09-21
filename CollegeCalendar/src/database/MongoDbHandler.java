package database;


import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.MongoDatabase;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



/**
 * 
 * @author Abhi Gupta + Gus :)
 *
 */


public class MongoDbHandler {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private String encryptionPassword;
	
	
	public MongoDbHandler() {
		MongoClientURI uri  = new MongoClientURI("mongodb://tester:tester1@ds135441.mlab.com:35441/408calendar");
		mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase(uri.getDatabase());
	}
	
	
	/*
	 * Main method for the database handler
	 */
	public static void main(String[] args) throws Exception {
		
		MongoDbHandler handler = new MongoDbHandler();
		
		ClassDBO cdbo = new ClassDBO(database);
		

//		boolean result = handler.isValidUser("testUser", "testPassword");
//		if (result) {
//			System.out.println("Successful login");
//		}
//		else {
//			System.out.println("Unsuccessful login");
//		}

		//String test = "test";
		//cdbo.insertClasses(test,test,test,test,test,test,test);
		//cdbo.deleteClassEvent("5ba18a42e0fec8158f661f89");
		//cdbo.getClassEvent("5ba18a42e0fec8158f661f89");
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
