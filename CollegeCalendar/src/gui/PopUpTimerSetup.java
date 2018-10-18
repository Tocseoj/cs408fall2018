package gui;

import java.awt.Toolkit;
import java.util.Timer;

public class PopUpTimerSetup {
	Timer		popUpTimer;
	Toolkit		popUpToolkit;	

	public PopUpTimerSetup(GUIController guiCont) {
		popUpToolkit = Toolkit.getDefaultToolkit();
		popUpTimer = new Timer();
		popUpTimer.schedule(new PopUpHandler(guiCont), 0, 20*1000);
	}
	
	public void timerExit() {
		popUpTimer.cancel();
	}
}
