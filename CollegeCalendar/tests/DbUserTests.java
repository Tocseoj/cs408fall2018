import database.DbUserHandler;
import junit.framework.Assert;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
//import org.junit.Assert;

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

public class DbUserTests {
	

	/*
	 * Tests whether a user was successfully added to the database
	 */
	@Test
	public void testInsertUser() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
//		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		
		
		dbuh.insertUser("testing_database_user", "password", "9/1/2018", "12/18/2018", "black");
		Document insertedUser = dbuh.getUserByUsername("testing_database_user");
		dbuh.deleteUserByUsername("testing_database_user");
		
		mongoClient2.close();
		
		assertNotNull(insertedUser);
	}
	
	/*
	 * Tests whether the database successfully deletes a user.
	 */
	@Test
	public void testDeleteUser() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		
		dbuh.insertUser("testing_database_user", "password", "9/1/2018", "12/18/2018", "black");
		dbuh.deleteUserByUsername("testing_database_user");
		Document insertedUser = dbuh.getUserByUsername("testing_database_user");
		
		mongoClient2.close();
		assertNull(insertedUser);
	}
	
	/*
	 * Tests whether the database successfully returns a list of all the users currently registered 
	 */
	@Test
	public void testGetAllUsers() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		
		MongoCollection<Document> collection = dbuh.getAllUsers();
		
		assertNotNull(collection);
	}
	
	/*
	 * Tests whether the passwords in the database are encrypted
	 */
	@Test
	public void testEncryption() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		// insert the user used for testing
		dbuh.insertUser("testing_database_user", "password", "9/1/2018", "12/18/2018", "black");
		Document insertedUser = dbuh.getUserByUsername("testing_database_user");
		String db_password = insertedUser.get("password", String.class);
		
		
		// delete the user in the database used for testing
		dbuh.deleteUserByUsername("testing_database_user");
		
		// if the boolean is true, then the password is not encrypted, so the test should fail
		boolean isNotEncrypted = "password".equals(db_password);
		
		// assertion to check the status of the boolean
		Assert.assertFalse(isNotEncrypted);
	}
	
	
	/*
	 * Tests whether a user can be successfully updated
	 */
	@Test
	public void testUpdateUser() throws Exception {
		MongoClient mongoClient2 = new MongoClient(new MongoClientURI("mongodb://ag_tester:testing123@ds135441.mlab.com:35441/408calendar"));
		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		
		// insert the user used for testing
		dbuh.insertUser("testing_database_user", "password", "9/1/2018", "12/18/2018", "black");
		dbuh.updateUser("testing_database_user", "password", "9/1/2018", "12/12/2018", "green");
		// get the user from the database
		Document insertedUser = dbuh.getUserByUsername("testing_database_user");
		String changed_color = insertedUser.get("color", String.class);  
		
		boolean isNotChanged = "black".equals(changed_color);
		
		System.out.println(isNotChanged);
		
		// delete the user used for testing, leaving the db untouched
		dbuh.deleteUserByUsername("testing_database_user");
		
		
		Assert.assertFalse(isNotChanged);
	}
	
	
	
}
