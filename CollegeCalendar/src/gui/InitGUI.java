package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InitGUI extends Application {
	
	private static double 	sceneWidth;
	private static double 	sceneHeight;
	private static Color	backgroundColor;
	
	public static void main(String[] args) {
		
		//
		// Setup static variables
		//
		sceneWidth = 1024;
		sceneHeight = 640;
		backgroundColor = Color.DARKSLATEGRAY;
		
		// Needed for JavaFX
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/*
		 * 		Initialize Static GUI Elements
		 */
		
		VBox container1 		= new VBox();				// Separates top button bar from rest of UI
		
		//
		// Container 1 Elements
		//
		HBox topBarContainer	= new HBox();				// Contains top row of buttons
		HBox container2 		= new HBox();				// Contains Grid view and event creation buttons
		
		//
		// Top Bar Container Elements
		//
		Button leftArrow		= new Button("<");			// Moves to previous month (or week)
		Button rightArrow		= new Button(">");			// Moves to next month/week
		Button todayButton		= new Button("Today");		// Jumps to current month/week
		ComboBox<String> viewSelector						// Drop-down selector for view type
								= new ComboBox<>();
		viewSelector.getItems()
			.addAll("Calendar View", "Weekly View");
		
		//
		// Container 2 Elements
		//
		Pane calendarView		= new Pane();				// Will display selected event view (is dynamic)
		VBox actionList			= new VBox();				// Will be list of actions available (add event, view analysis, etc.)
		
		//
		// ActionList Elements
		//
		Button addEventButton	= new Button("Add Event");	// Adds event from dialog to calendar
		
		
		/*
		 * 		Initialize Standard Scene and Stage
		 */
		
		//
		// Add elements to Scene
		//
		actionList.getChildren().addAll(addEventButton);
		
		container2.getChildren().addAll(calendarView, actionList);
		topBarContainer.getChildren().addAll(leftArrow, rightArrow, todayButton, viewSelector);
		
		container1.getChildren().addAll(topBarContainer, container2);
		
		//
		// Display Scene/Window
		//	
		StackPane root = new StackPane();
		root.getChildren().add(container1);

		Scene scene = new Scene(root, sceneWidth, sceneHeight, backgroundColor);
		scene.getStylesheets().add("gui/joe-gui.css");

		primaryStage.setTitle("College Calendar");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}
