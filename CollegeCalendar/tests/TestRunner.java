import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(DbUserTests.class);
      Result meetingResults = JUnitCore.runClasses(DbMeetingTests.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      for (Failure failure : meetingResults.getFailures()) {
    	  System.out.println(failure.toString());
      }
      
      System.out.println("Status of user testing success:" + result.wasSuccessful());
      System.out.println("Status of meeting testing success:" + meetingResults.wasSuccessful());
   }
}  	