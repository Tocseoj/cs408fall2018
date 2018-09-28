package gui;

import java.awt.Toolkit;
import java.util.Timer;

public class NotificationTimerSetup {
	Timer		notificationTimer;
	Toolkit		notificationToolkit;	

	public NotificationTimerSetup() {
		notificationToolkit = Toolkit.getDefaultToolkit();
		notificationTimer = new Timer();
		notificationTimer.schedule(new NotifyTask(), 0, 2*1000);
	}
}