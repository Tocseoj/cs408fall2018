package database;


import java.util.Arrays;
import java.util.List;

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
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * 
 * @author Abhi Gupta
 *
 */


public class MongoDbHandler {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private String encryptionPassword;
	
	
	public MongoDbHandler() {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("408testdb");
	}
	
	
	/*
	 * Main method for the database handler
	 */
	public static void main(String[] args) throws Exception {
		
		MongoDbHandler handler = new MongoDbHandler();
		
		
		boolean result = handler.isValidUser("testUser", "testPassword");
		if (result) {
			System.out.println("Successful login");
		}
		else {
			System.out.println("Unsuccessful login");
		}
	}
	
	
	/*
	 * Inserts a user with the given username and password in the database, under
	 * users collection
	 * 
	 * @param username the username of the new user
	 * @param password the password of the new user
	 */
	void insertUser(String username, String password) throws Exception {
		// need to check if a user already exists with the given user name
		Document oldUser = getUserByUsername(username);
		if (oldUser != null) {
			throw new java.lang.Error("User already exists");
		}
		
		String encryptionPassword = "testing_password";
		
		// do some password encryption stuff
		byte[] salt = new String("12345678").getBytes();
		
		// using a small-ish number for speed
		int iterationCount = 400;
		
		int keyLength = 128;
		SecretKeySpec key = createSecretKey(encryptionPassword.toCharArray(), salt, iterationCount, keyLength);
		
		String encryptedPassword = encrypt(password, key); // this is the password that should be stored in the database
		
		
		MongoCollection<Document> collection = database.getCollection("users");
		Document newUser = new Document("username", username)
				.append("password", encryptedPassword);
		
		collection.insertOne(newUser);
	}
	
	/*
	 * Checks whether the user with the given username and password exists in the database
	 * @param username the username to check in the database
	 * @param password the password to check in the database
	 * @return true 	if the user exists in the database
	 * 		   false	if the user does not exist in the database
	 */
	boolean isValidUser(String username, String password) throws GeneralSecurityException, IOException {
		// get the user from the database with the given username
		Document user = getUserByUsername(username);
		
		if (user == null) {
			// the username is not in the database
			System.out.println("Invalid username");
			return false;
		}
		
		
		String db_username = (String) user.get("username");
		String db_password = (String) user.get("password");
		
		
		// decrypt db_password
		// do some password encryption stuff
		byte[] salt = new String("12345678").getBytes();
		
		// using a small-ish number for speed
		int iterationCount = 400;
		
		int keyLength = 128;
		// testing_password is used for encryption, secure this later
		SecretKeySpec key = createSecretKey("testing_password".toCharArray(), salt, iterationCount, keyLength); 
		String decryptedPassword = decrypt(db_password, key);
		
		if (password.equals(decryptedPassword)) {
			return true;
		}
		else {
			return false;
		}		
	}
	
	/*
	 * Decrypts a given string using the key that was computed previously
	 * @return the decrypted string
	 */
	private String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException{
		String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	}


	private byte[] base64Decode(String property) {
		// TODO Auto-generated method stub
		return Base64.getDecoder().decode(property);
	}

	/*
	 * Encrypts a given string given the key that was computed earlier
	 * @return the encrypted string
	 */
	private String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
		Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
	}


	private String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}


	private SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
	}


	/*
	 * Get all the users currently in the database
	 */
	MongoCollection<Document> getAllUsers() {
		MongoCollection<Document> collection = database.getCollection("users");
		return collection;
	}
	
	
	/*
	 * Queries the database to get a user by user name
	 */
	Document getUserByUsername(String username) {
		MongoCollection<Document> collection = database.getCollection("users");
		Document user = collection.find(eq("username", username)).first();
		
		return user;
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
