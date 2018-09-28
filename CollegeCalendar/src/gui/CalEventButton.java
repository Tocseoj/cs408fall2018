package gui;

import java.time.Duration;
import java.time.LocalTime;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CalEventButton extends Button {
	protected double startRatio = 0;
	
	protected EventGO baseEvent;
	protected ClickHandler clickedHand;
	
	public static final Color defColor = Color.CORNSILK;
	
	public CalEventButton() {
		super();
		clickedHand = new ClickHandler();
		
		setOnAction(clickedHand);
		setBackground(new Background(new BackgroundFill(defColor, null, null)));
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
		}
	}
}
