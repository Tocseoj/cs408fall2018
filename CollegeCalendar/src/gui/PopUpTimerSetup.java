package gui;

import java.awt.Toolkit;
import java.util.Timer;

public class PopUpTimerSetup {
	/* Timer and handler varaibles */
	Timer		popUpTimer;
	Toolkit		popUpToolkit;
	
	PopUpHandler handler;

	/* Build and schedule timer thread to run every 20 seconds
	 * 	This ensures PopUpHandler is called at least once every minute
	 * 	and prevents any bottle-necking when traversing eventList
	 */
	public PopUpTimerSetup(GUIController guiCont) {
		popUpToolkit = Toolkit.getDefaultToolkit();
		popUpTimer = new Timer();
		popUpTimer.schedule(handler = new PopUpHandler(guiCont), 0, 20*1000);
	}
	
	/*
	 *  Shut down timer gracefully.
	 * 	This is called when the main JavaFX thread
	 * 	closes (stop())
	 */
	public void timerExit() {
		handler.cancel();
		popUpTimer.cancel();
	}
}
