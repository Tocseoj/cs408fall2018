package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainGUI extends Application{
	
	private Controller ic = new Controller();				// Variable to talk to Database
	
	private ArrayList<GenericGO> genericList;				// Event list to display on GUI

	private String userName = "testUser";					// Sprint 2 add user account
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {

			genericList = ic.getAllGenerics(userName);		// Fill List with objects from database

			// Generate window and show
			Scene scene = fxTest();
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Good Shit");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Generates window and elements and add Event handlers to elements
	public Scene fxTest() {
		
		// Setup main window with width 1280 and height 840
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1280, 840);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		// Add rounded rectangle (bounds buttons)
		Rectangle rect = new Rectangle();
		rect.setX(0.0); 
		rect.setY(0.0); 
		rect.setWidth(150.0); 
		rect.setHeight(150.0d);
		rect.setArcWidth(30.0);
		rect.setArcHeight(20.0);
		rect.setFill(Color.ALICEBLUE);
		
		// Add Blue Rectangle to top center
		Rectangle rect2 = new Rectangle(400.0, 100.0, Color.BLUEVIOLET);
		rect2.setArcWidth(30.0);
		rect2.setArcHeight(20.0);
		BorderPane.setAlignment(rect2, Pos.TOP_CENTER);
		
		// Add Rectangle to bottom center?
		Rectangle rect3 = new Rectangle(400.0, 100.0, Color.CORAL);
		rect3.setArcWidth(30.0);
		rect3.setArcHeight(20.0);
		BorderPane.setAlignment(rect3, Pos.TOP_CENTER);

		// Add Buttons
		Button storeDataButton = new Button("Store in Database");
		Button getDataButton = new Button("Get All Events");
		// Add to button Group
		VBox buttonGroup = new VBox();
		buttonGroup.getChildren().add(storeDataButton);
		buttonGroup.getChildren().add(getDataButton);
		
		// Add shit to scene
		root.getChildren().add(rect);
		root.setCenter(buttonGroup);
		root.setTop(rect2);
		root.setBottom(rect3);
		scene.setFill(Color.WHITE);
		
		
		// "Store in Database" Event handler
		storeDataButton.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {				
				System.out.println("Saved to database. TODO");
			}
		});
		// "Get All Events" Event handler
		getDataButton.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				System.out.println("Getting from database? TODO");
			}
		});
		
		return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Start Joe's Work
	public void addEvent(String id, String title, LocalDate date, LocalTime time, Duration duration) {
		GenericGO newEvent = new GenericGO("0", "test event");
		genericList.add(newEvent);
		ic.addEventToDatabase(newEvent);
	}
	public void addClass(String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, LocalDate endRepeat, Duration notificationOffset) {
		GenericGO newEvent = new GenericGO("0", "test event");
		genericList.add(newEvent);
		ic.addEventToDatabase(newEvent);
	}
	// End Joe's Work

}
