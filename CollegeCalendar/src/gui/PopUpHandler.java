package gui;

import java.util.TimerTask;

public class PopUpHandler extends TimerTask {

	GUIController guiConttroller;
	
	public PopUpHandler(GUIController guiCont) {
		
		
	}
	
	public void run() {
		guiConttroller.handlePopUps();
	}
	
}
