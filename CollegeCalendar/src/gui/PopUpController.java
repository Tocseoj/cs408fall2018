package gui;


import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class PopUpController {
	
	public static JDialog popUpStage;
	
	public PopUpController() {
		popUpStage = new JDialog();
		popUpStage.setAlwaysOnTop(true);
		popUpStage.setModalityType(null);
	}
	
	/*
	 * Simple GUI pop-up for completed event reminder 
	 */
	public void eventCompleted(EventGO e) {
		popUpStage.setTitle("Event Completion");
		String message = "Event: "+e.getTitle()+" has ended!";
		
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(popUpStage, message);
	}
	
	/*
	 * Simple GUI pop-up for current event reminder 
	 */
	public void remindUser(EventGO e) {
		popUpStage.setTitle("Event Reminder");
		String message = "Event: "+e.getTitle()+" is going on right now!";
		
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(popUpStage, message);
	}
	
	/*
	 * Simple GUI pop-up for upcoming event reminder 
	 */
	public void notifyUpcomingEvent(EventGO e) {
		popUpStage.setTitle("Event Notification");
		String message = "Event: "+e.getTitle()+" starts in (or less than) "
				+e.getNotificationOffset().toMinutes() + " minutes!";
		
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(popUpStage, message);
	}
}
