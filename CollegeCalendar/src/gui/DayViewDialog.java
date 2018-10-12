package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.DateAndTimeManager;
import controller.EventType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DayViewDialog {

	public DayViewDialog(LocalDate day, Stage primaryStage, GUIController guiController) {
		
		int width = 400;
		int height = 600;

		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);

		ScrollPane scroll = new ScrollPane();
		scroll.setPrefSize(width, height);

		VBox dialogVbox = new VBox();
		ArrayList<EventGO> dayEvents = guiController.getEvents(day, day, 1).get(0); // TODO
		for (int i = 0; i < dayEvents.size(); i++) {
			EventGO event_go = dayEvents.get(i);
			Button event = new Button(event_go.getTitle());
			event.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					new EventDialog(primaryStage, event_go, guiController);
					dialog.close();
				}
			});
			dialogVbox.getChildren().add(event);
		}
		if (dayEvents.size() <= 0) {
			dialogVbox.getChildren().add(new Label("No Events on Day " + day.getDayOfMonth()));
		}
		scroll.setContent(dialogVbox);
		
		// Add Event Button
		Button b = new Button("Add Event");
		b.setOnAction((event) -> {
			EventGO e = new EventGO(EventType.GENERIC, "",  "", day, DateAndTimeManager.getCurrentTime(), Duration.ZERO, 0, new Boolean[0], day, Duration.ZERO, false, guiController.getUsername(), false, false);
		    new EventDialog(primaryStage, e, guiController);
		    dialog.close();
		});
		
		// Container
		VBox container = new VBox();
		container.getChildren().addAll(scroll, b);

		Scene dialogScene = new Scene(container, width, height);
		dialog.setScene(dialogScene);
		dialog.setTitle("Viewing Events on Day " + day);
		dialog.show();
		
	}
	
}
