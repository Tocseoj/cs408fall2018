package gui;

import java.util.TimerTask;

public class PopUpHandler extends TimerTask {

	private GUIController guiConttroller;
	
	public PopUpHandler(GUIController guiCont) {
		this.guiConttroller = guiCont;
	}
	
	public void run() {
		guiConttroller.handlePopUps();
	}
	
}
