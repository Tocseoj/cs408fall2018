package gui;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsDialog {
	
	public SettingsDialog(Stage primaryStage, GUIController guiController) {
		
		VBox container = new VBox();
		Label startDateLabel = new Label("Set Start of Semester.");
		DatePicker startDate = new DatePicker();
		
		Label endDateLabel = new Label("Set End of Semester.");
		DatePicker endDate = new DatePicker();

		Button submitButton = new Button("Submit");
		
		//
		// Event Handlers
		//
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				
				LocalDate start = startDate.getValue();
				LocalDate end = endDate.getValue();
				
				guiController.setStartDate(start);
				guiController.setEndDate(end);
			}
		});
		
		
		//
		// Setup Scene
		//
		
		container.getChildren().addAll(startDateLabel, startDate, endDateLabel, endDate, submitButton);
		
		
		StackPane loginRoot = new StackPane();
		loginRoot.getChildren().add(container);
		
		Scene scene = new Scene(loginRoot, 200, 150);
		
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		
		dialog.setScene(scene);
		dialog.setTitle("Settings");
		dialog.show();
		
	}

}
