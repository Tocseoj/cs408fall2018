package gui;

import java.time.Duration;
import java.time.LocalTime;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CalEventButton extends Button {
	protected double startRatio = 0;
	
	protected EventGO baseEvent;
	protected ClickHandler clickedHand;
	
	public CalEventButton() {
		super();
		clickedHand = new ClickHandler();
		
		setOnAction(clickedHand);
	}
	public CalEventButton(EventGO base) {
		this();
		baseEvent = base;
		setText(base.getTitle());
	}
	
	class ClickHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// TODO Currently only testing output, implement editing later
			
			System.out.print("Event Time: ");
			System.out.println(baseEvent.getTime());
			
			System.out.print("Event Duration: ");
			System.out.print(baseEvent.getDuration().toMinutes());
			System.out.println(" Minutes");
		}
	}
}
