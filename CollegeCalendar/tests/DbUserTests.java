//import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoCollection;

import database.UserDBO;
import junit.framework.Assert;

public class DbUserTests {
	

	/*
	 * Tests whether a user was successfully added to the database
	 */
	@Test
	public void testInsertUser() throws Exception {
//		DbUserHandler dbuh = new DbUserHandler(mongoClient2);
		UserDBO dbuh = new UserDBO();
		
		
		dbuh.insertUser("testing_database_user", "password", "9/1/2018", "12/18/2018", "black");
		Document insertedUser = dbuh.getUserByUsername("testing_database_user");
		dbuh.deleteUserByUsername("testing_database_user");
				
		assertNotNull(insertedUser);
	}
	
	/*
	 * Tests whether the database successfully deletes a user.
	 */
	@Test
	public void testDeleteUser() throws Exception {
		UserDBO dbuh = new UserDBO();
		
		dbuh.insertUser("testing_database_user", "password", "9/1/2018", "12/18/2018", "black");
		dbuh.deleteUserByUsername("testing_database_user");
		Document insertedUser = dbuh.getUserByUsername("testing_database_user");
		
		assertNull(insertedUser);
	}
	
	/*
	 * Tests whether the database successfully returns a list of all the users currently registered 
	 */
	@Test
	public void testGetAllUsers() throws Exception {
		UserDBO dbuh = new UserDBO();
		
		MongoCollection<Document> collection = dbuh.getAllUsers();
		
		assertNotNull(collection);
	}
	
	/*
	 * Tests whether the passwords in the database are encrypted
	 */
	@Test
	public void testEncryption() throws Exception {
		UserDBO dbuh = new UserDBO();
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
		UserDBO dbuh = new UserDBO();
		
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
