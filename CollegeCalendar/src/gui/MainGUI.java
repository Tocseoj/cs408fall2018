package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import controller.Controller;
import controller.SortByDate;
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
	private Controller ic = new Controller();
	private ArrayList<EventGO> eventList;
	private ArrayList<Button> buttonList;
	
	private String userName = "testUser";
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			eventList = ic.getAllEvents(userName);
			buttonList = getAllEventButtons();
			sortButtonList();
			Scene scene = fxTest();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setTitle("JavaFX Test");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sortButtonList() {
		Collections.sort(buttonList, new SortByDate());
	}
	
	//incomplete method to add buttons to button list
	public ArrayList<Button> getAllEventButtons(){
		ArrayList<Button> b = new ArrayList<Button>();
		Iterator<EventGO> im = eventList.iterator();
		while(im.hasNext()) {
			Button storeDataButton = new Button("Store in Database");
			storeDataButton.setUserData(im.next());
			storeDataButton.setOnAction(new EventHandler<ActionEvent> () {
				public void handle(ActionEvent e) {
					EventGO asd = (EventGO)storeDataButton.getUserData();
					//some functionality
					System.out.println(ic.getEventNameById(asd));
				}
			});
			b.add(storeDataButton);
		}
		return b;
	}
	
	public Scene fxTest() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,500,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		Rectangle rect = new Rectangle();
		rect.setX(0.0); 
		rect.setY(0.0); 
		rect.setWidth(150.0); 
		rect.setHeight(150.0d); 
		
		rect.setArcWidth(30.0);
		rect.setArcHeight(20.0);
		
		Rectangle rect2 = new Rectangle(400.0, 100.0, Color.BLUEVIOLET);
		rect2.setArcWidth(30.0);
		rect2.setArcHeight(20.0);
		BorderPane.setAlignment(rect2, Pos.TOP_CENTER);
		
		Rectangle rect3 = new Rectangle(400.0, 100.0, Color.DARKORANGE);
		rect3.setArcWidth(30.0);
		rect3.setArcHeight(20.0);
		BorderPane.setAlignment(rect3, Pos.TOP_CENTER);
		
		VBox buttonGroup = new VBox();
		
		EventGO cgo = new EventGO("5ba3b32818f6310f6c7d8cde","haaa");

		Button storeDataButton = new Button("Store in Database");
		buttonGroup.getChildren().add(storeDataButton);
		storeDataButton.setUserData(cgo);
		Button getDataButton = new Button("Get All Events");
		buttonGroup.getChildren().add(getDataButton);
		
		
		//Eventhandler<MouseEvent>
		storeDataButton.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				EventGO asd = (EventGO)storeDataButton.getUserData();
				
				System.out.println(ic.getEventNameById(asd));
			}
		});
		
		getDataButton.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				System.out.println("Getting");
			}
		});
		
		//root.getChildren().add(rect);
		root.setCenter(buttonGroup);
		root.setTop(rect2);
		root.setBottom(rect3);
		
		scene.setFill(Color.WHITE);
		
		return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Start Joe's Work
	public void addEvent(String id, String title, LocalDate date, LocalTime time, Duration duration) {
		EventGO newEvent = new EventGO("0", "test event");
		eventList.add(newEvent);
		ic.addEventToDatabase(newEvent);
	}
	public void addClass(String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, LocalDate endRepeat, Duration notificationOffset) {
		EventGO newEvent = new EventGO("0", "test event");
		eventList.add(newEvent);
		ic.addEventToDatabase(newEvent);
	}
	// End Joe's Work

}
