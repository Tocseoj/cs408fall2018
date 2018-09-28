package gui;

import java.time.LocalTime;
import java.util.TimerTask;

public class NotifyTask extends TimerTask {
	LocalTime timeCheck;
	
	public void run() {
		JoeGUI.pollToNotify();
	}
}
