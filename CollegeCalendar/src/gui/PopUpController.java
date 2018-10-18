package gui;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopUpController {
	
	//public static Stage popUpStage;
	public static JFrame popUpStage;
	
	public PopUpController() {
		//popUpStage = new Stage();
		popUpStage = new JFrame();
	}
	
	/*
	 * Simple GUI pop-up for completed event reminder 
	 */
	public void eventCompleted(EventGO e) {
		popUpStage.setTitle("Event Completion");
		String message = "Event: "+e.getTitle()+" has ended!";
		
		JOptionPane.showMessageDialog(popUpStage, message);
	}
	
	/*
	 * Simple GUI pop-up for current event reminder 
	 */
	public void remindUser(EventGO e) {
		popUpStage.setTitle("Event Reminder");
		String message = "Event: "+e.getTitle()+" is going on!";
	
		JOptionPane.showMessageDialog(popUpStage, message);
	}
	
	/*
	 * Simple GUI pop-up for upcoming event reminder 
	 */
	public void notifyUpcomingEvent(EventGO e) {
		popUpStage.setTitle("Event Notification");
		String message = "Event: "+e.getTitle()+" starts in "
				+e.getNotificationOffset().toMinutes() + " minutes!";
		
		JOptionPane.showMessageDialog(popUpStage, message);
	}
}
