package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InitGUI extends Application {
	
	private static double 			sceneWidth;
	private static double 			sceneHeight;
	private static Color			backgroundColor;
	private static GUIController	guiController;
	
	public static void main(String[] args) {
		
		//
		// Setup static variables
		//
		sceneWidth = 1024;
		sceneHeight = 640;
		backgroundColor = Color.DARKSLATEGRAY;
		guiController = null;
		
		// Needed for JavaFX
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		guiController = new GUIController(primaryStage, sceneWidth, sceneHeight, backgroundColor);
		
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
		Region topBarPadding	= new Region();				// Fills extra space
		ComboBox<String> viewSelector						// Drop-down selector for view type
								= new ComboBox<>();
		viewSelector.getItems()
			.addAll("Calendar View", "Weekly View");
		viewSelector.setValue("Calendar View");
		
		//
		// Container 2 Elements
		//
		Pane calendarPane									// Will display selected event view (is dynamic)
								= guiController.getDynamicPane();
		VBox actionList			= new VBox();				// Will be list of actions available (add event, view analysis, etc.)
		
		//
		// ActionList Elements
		//
		Button addEventButton	= new Button("Add Event");	// Adds event from dialog to calendar
		
		
		//
		// Setup Event Handlers
		//
		leftArrow.setOnAction(this::leftArrow);
		rightArrow.setOnAction(this::rightArrow);
		todayButton.setOnAction(this::todayButton);
		viewSelector.setOnAction(this::viewDropdown);
		
		addEventButton.setOnAction(this::addEventButton);
		
		/*
		 * 		Initialize Standard Scene and Stage
		 */
		
		// Grow styling
		HBox.setHgrow(topBarPadding, Priority.ALWAYS);
		VBox.setVgrow(container2, Priority.ALWAYS);
		HBox.setHgrow(calendarPane, Priority.ALWAYS);
		
		//
		// Add elements to Scene
		//
		actionList.getChildren().addAll(addEventButton);
		
		container2.getChildren().addAll(calendarPane, actionList);
		topBarContainer.getChildren().addAll(leftArrow, rightArrow, todayButton, topBarPadding, viewSelector);
		
		container1.getChildren().addAll(topBarContainer, container2);
		
		//
		// Display Scene/Window
		//	
		StackPane root = new StackPane();
		root.getChildren().add(container1);
		actionList.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		Scene scene = new Scene(root, sceneWidth, sceneHeight, backgroundColor);
//		scene.getStylesheets().add("gui/joe-gui.css"); TODO: Add styling

		primaryStage.setTitle("College Calendar");
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		guiController.showUsernamePopup(); //DEBUG
	}
	
	private void leftArrow(ActionEvent event) {
		guiController.leftArrow();
	}
	private void rightArrow(ActionEvent event) {
		guiController.rightArrow();
	}
	private void todayButton(ActionEvent event) {
		guiController.todayButton();
	}
	private void viewDropdown(ActionEvent event) {
		ComboBox<String> cb = (ComboBox<String>)event.getSource();
		guiController.switchView(cb.getValue());
	}
	private void addEventButton(ActionEvent event) {
		guiController.addEventButton();
	}
}
