package gui;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopUpController {
	
	public static Stage popUpStage;
	
	public PopUpController() {
		popUpStage = new Stage();
		
	}
	
	public static void notifyUpcomingEvent(EventGO e) {
		popUpStage.setTitle("Event Notification");
		String message = "Event: "+e.getTitle()+" starts in "
				+e.getNotificationOffset().toMinutes() + " minutes!";
		
		Text displayText = new Text(10, 40, message);
		displayText.setFont(new Font(20));
		
		Scene popUp = new Scene(new Group(displayText));
		
		popUpStage.setScene(popUp);
		popUpStage.sizeToScene();
		popUpStage.show();
	}
	
	public static void remindUser(EventGO e) {
		popUpStage.setTitle("Event Reminder");
		String message = "Event: "+e.getTitle()+" is going on!";
		
		Text displayText = new Text(10, 40, message);
		displayText.setFont(new Font(20));
		
		Scene popUp = new Scene(new Group(displayText));
		
		popUpStage.setScene(popUp);
		popUpStage.sizeToScene();
		popUpStage.show();
	}
	
	public static void eventCompleted(EventGO e) {
		popUpStage.setTitle("Event Completion");
		String message = "Event: "+e.getTitle()+" has just ended!";
		
		Text displayText = new Text(10, 40, message);
		displayText.setFont(new Font(20));
		
		Scene popUp = new Scene(new Group(displayText));
		
		popUpStage.setScene(popUp);
		popUpStage.sizeToScene();
		popUpStage.show();
	}
}
