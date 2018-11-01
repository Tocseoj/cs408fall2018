package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
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
	
	
	PopUpTimerSetup 	popUp;
	public static boolean		killThread = false;
	
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
		StackPane calendarPane	= new StackPane();			// Will display selected event view (is dynamic)
		VBox actionList			= new VBox();				// Will be list of actions available (add event, view analysis, etc.)
		
		//
		// Setup GUIController
		//
		guiController = new GUIController(primaryStage, calendarPane);
		
		//
		// ActionList Elements
		//
		Button addEventButton	= new Button("Add Event");	// Adds event from dialog to calendar
		Button addContactButton = new Button("Manage Contacts");
		Region sideBarPadding	= new Region();				// Fills extra space
		Button changeAccountButton 							// Let's you select your username
								= new Button("Change Account");
		
		//
		// Setup Event Handlers
		//
		leftArrow.setOnAction(this::leftArrow);
		rightArrow.setOnAction(this::rightArrow);
		todayButton.setOnAction(this::todayButton);
		viewSelector.setOnAction(this::viewDropdown);
		
		addEventButton.setOnAction(this::addEventButton);
		addContactButton.setOnAction(this::addContactButton);
		changeAccountButton.setOnAction(this::changeAccount);
		
		/*
		 * 		Initialize Standard Scene and Stage
		 */
		
		// Grow styling
		HBox.setHgrow(topBarPadding, Priority.ALWAYS);
		VBox.setVgrow(sideBarPadding, Priority.ALWAYS);
		VBox.setVgrow(container2, Priority.ALWAYS);
		HBox.setHgrow(calendarPane, Priority.ALWAYS);
		
		//
		// Add elements to Scene
		//
		actionList.getChildren().addAll(addEventButton, addContactButton, sideBarPadding, changeAccountButton);
		
		container2.getChildren().addAll(calendarPane, actionList);
		topBarContainer.getChildren().addAll(leftArrow, rightArrow, todayButton, topBarPadding, viewSelector);
		
		container1.getChildren().addAll(topBarContainer, container2);
		
		//
		// Display Scene/Window
		//	
		StackPane root = new StackPane();
		root.getChildren().add(container1);
//		calendarPane.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY))); //TODO

		Scene scene = new Scene(root, sceneWidth, sceneHeight, backgroundColor);
//		scene.getStylesheets().add("gui/joe-gui.css"); TODO: Add styling

		primaryStage.setTitle("College Calendar");
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		changeAccount(null); TODO
		
		/* 
		 * Initialize and start running popUp checker
		 */
		popUp = new PopUpTimerSetup(guiController);
	}
	
	
	@Override
	/*
	 * Shut down PopUp thread along with main JavaFX thread
	 */
	public void stop() throws Exception {
		System.out.println("Exiting");
		killThread = true;
		popUp.timerExit();
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
		@SuppressWarnings("unchecked")
		ComboBox<String> cb = (ComboBox<String>)event.getSource();
		guiController.switchView(cb.getValue());
	}
	private void addContactButton(ActionEvent event) {
		guiController.addContactButton();
	}
	private void addEventButton(ActionEvent event) {
		guiController.addEventButton();
	}
	private void changeAccount(ActionEvent event) {
		guiController.showUsernamePopup();
	}
}
