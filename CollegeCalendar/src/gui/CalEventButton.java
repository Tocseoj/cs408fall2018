package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import database.EventDBO;

public class CalEventButton extends Button {
	protected int time = 0;
	protected int duration = 30;
	protected EventDBO baseEvent;
	protected ClickHandler clickedHand;
	public CalEventButton() {
		super();
		clickedHand = new ClickHandler();
		
		setOnAction(clickedHand);
	}
	public CalEventButton(String name) {
		this();
		setText(name);
	}
	
	class ClickHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// TODO Currently only testing output, implement editing later
			
			System.out.print("Event Time: ");
			System.out.println(time);
			System.out.print("Event Time: ");
			System.out.println(duration);
		}
	}
}
