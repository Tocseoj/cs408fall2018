package gui;

import java.util.TimerTask;

public class PopUpHandler extends TimerTask {

	private GUIController guiConttroller;
	
	public PopUpHandler(GUIController guiCont) {
		this.guiConttroller = guiCont;
	}
	
	/* Set timer task to run the PopUpHandler every 20 seconds */
	public void run() {
		guiConttroller.handlePopUps();
	}
}
