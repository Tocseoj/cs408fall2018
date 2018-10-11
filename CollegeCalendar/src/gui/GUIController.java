package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import controller.DateAndTimeManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * 
 * 		Manages all data that needs to be kept for events and GUI Control
 * 
 */

public class GUIController {
	
	private Stage primaryStage;
	private Paint backgroundColor;
	
	private CalendarViews calendarView;
	private DateAndTimeManager date;
	private String username;
	private Pane dynamicPane;
	
	private StackPane calendarPane;
	
	private Controller controller;
//	private HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<EventGO>>>> events;
	private ArrayList<EventGO> eventList;
	
	public GUIController (Stage primaryStage, StackPane calendarPane) {
		
		this.primaryStage = primaryStage;
		this.calendarPane = calendarPane;
		
		username = "tester";
		
		date = new DateAndTimeManager();

		controller = new Controller();
		eventList = controller.getAllEvents(username);
		
//		for (EventGO e : eventList) {
//			
//			LocalDate d = e.getDate();
//			ArrayList<EventGO> dayList;
//			
//		}
		
		calendarView = new MonthlyGUI(this);
		
		updatePane();

	}
	
	public GUIController(Stage primaryStage,  double sceneWidth, double sceneHeight, Paint backgroundColor) {
		this.primaryStage = primaryStage;
//		this.sceneWidth = sceneWidth;
//		this.sceneHeight = sceneHeight;
		this.backgroundColor = backgroundColor;
		
		controller = new Controller();
		
		calendarView = new MonthlyGUI(this);
		date = new DateAndTimeManager();
		username = "tester";
		
		dynamicPane = calendarView.getCalendarView(date.getViewingDate());

	}
	
	public void changePrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void showUsernamePopup() {
		
		VBox container = new VBox();
		Label inputLabel = new Label("Enter your username.");
		TextField inputField = new TextField();
		// DEBUG
		inputField.setText("tester");
		Button submitButton = new Button("Submit");
		
		//
		// Event Handlers
		//
		submitButton.setOnAction(this::handleUsernamePopupSubmit);
		
		
		//
		// Setup Scene
		//
		
		container.getChildren().addAll(inputLabel, inputField, submitButton);
		
		
		StackPane loginRoot = new StackPane();
		loginRoot.getChildren().add(container);
		
		Scene scene = new Scene(loginRoot, 200, 100, backgroundColor);
		
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		
		dialog.setScene(scene);
		dialog.setTitle("Username Form");
		dialog.show();
	}
	
	private void handleUsernamePopupSubmit(ActionEvent event) {
		Button submit = (Button)event.getSource();
		Pane dialogContainer = (Pane)submit.getParent();
		for (Node node : dialogContainer.getChildrenUnmodifiable()) {
			if (node instanceof TextField) {
				username = ((TextField)node).getText();
				if (username.equals("")) {
					username = null;
				}
				break;
			}
		}
		Stage dialog = (Stage)dialogContainer.getScene().getWindow();	
		if (username != null) {
			dialog.close();
			
			eventList = controller.getAllEvents(username);
			calendarView.updateEvents();
			updatePane();
		}
	}
	
	public Pane getDynamicPane() {
		return dynamicPane;
	}
	
	//
	// Refresh the calendar view from CalendarViews interface
	private void updatePane() {
		calendarPane.getChildren().remove(dynamicPane);
		dynamicPane = calendarView.getCalendarView(date.getViewingDate());
		calendarPane.getChildren().add(dynamicPane);
	}
	
	private void updateDynamicPane() {
		Pane parent = (Pane)dynamicPane.getParent();
		parent.getChildren().remove(dynamicPane);
		dynamicPane = calendarView.getCalendarView(date.getViewingDate());
		HBox.setHgrow(dynamicPane, Priority.ALWAYS);
		parent.getChildren().add(0, dynamicPane);
	}
	
	public void switchView(String viewName) {
		if (viewName.equals("Calendar View") || viewName.equals("Monthly View")) {
			if (calendarView instanceof MonthlyGUI) {
				return;
			}
			calendarView = new MonthlyGUI(this);
			updateDynamicPane();
		}
		else if (viewName.equals("Weekly View")) {
			if (calendarView instanceof WeeklyGUI) {
				return;
			}
			calendarView = new WeeklyGUI(controller, this);
			updateDynamicPane();
		}
	}
	
	public void leftArrow() {
		if (calendarView instanceof MonthlyGUI) {
			date.setViewingDate(date.getViewingDate().minusMonths(1));
		}
		else if (calendarView instanceof WeeklyGUI) {
			date.setViewingDate(date.getViewingDate().minusWeeks(1));
		}
		calendarView.updateEvents(); 
		updatePane();
	}
	public void rightArrow() {
		if (calendarView instanceof MonthlyGUI) {
			date.setViewingDate(date.getViewingDate().plusMonths(1));
		}
		else if (calendarView instanceof WeeklyGUI) {
			date.setViewingDate(date.getViewingDate().plusWeeks(1));
		}
		calendarView.updateEvents();
		updatePane();
	}
	public void todayButton() {
		date.setViewingDate(DateAndTimeManager.getCurrentDate());
		calendarView.updateEvents();
		updatePane();
	}
	public void addEventButton() {
		EventDialog ed = new EventDialog(primaryStage, null, this);
	}
	
//	public void addEventToView(EventGO event) {
//		calendarView.addEventToView(event);
//	}
//	
//	public void removeEventFromView(EventGO event) {
//		calendarView.removeEventFromView(event);
//	}
	
	public void showEventPopup(EventGO event) {
		new EventDialog(primaryStage, event, this);
	}
	
	//
	// Helper method to get all events in same month of specified day
	public ArrayList<ArrayList<EventGO>> getMonthEvents(LocalDate day) {
		
		int length = day.lengthOfMonth();
		LocalDate start = day.withDayOfMonth(1);
		LocalDate finish = day.withDayOfMonth(length);
		
		return getEvents(start, finish, length);
		
	}
	
	//
	// Helper method to get all events in dame week of specified day
	public ArrayList<ArrayList<EventGO>> getWeekEvents(LocalDate day) {
		
		int length = 7;
		int dayOfWeek = day.getDayOfWeek().getValue();
		dayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek;
		LocalDate start = day.minusDays(dayOfWeek);
		LocalDate finish = day.plusDays(7 - dayOfWeek);
		
		return getEvents(start, finish, length);
	}
	
	//
	// Get events between two dates with provided length between the dates
	public ArrayList<ArrayList<EventGO>> getEvents(LocalDate start, LocalDate finish, int length) {
		ArrayList<ArrayList<EventGO>> returnList = new ArrayList<>();

		
		for (int i = 0; i < length; i++) {	
			returnList.add(new ArrayList<EventGO>());	
		}
		
		for (EventGO event : eventList) {
			if (event.getID().equals("")) {
				System.out.println("Null Event Found!");
			}
			if ((event.getDate().isAfter(start) || event.getDate().isEqual(start)) && (event.getDate().isBefore(finish) || event.getDate().isEqual(finish))) {
				returnList.get((int)start.until(event.getDate(), ChronoUnit.DAYS)).add(event);
			}
			if (!event.getDate().isEqual(event.getEndRepeat())) {
				if (event.getEndRepeat().isAfter(start) || event.getEndRepeat().isEqual(start)) {
					for (int i = 0; i < length; i++) {
						LocalDate day = start.plusDays(i);
						int dayOfWeek = day.getDayOfWeek().getValue();
						dayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek;
						if (event.getRepeatDays()[dayOfWeek]) {
							if (day.isAfter(event.getDate()) && (day.isBefore(event.getEndRepeat()) || day.isEqual(event.getEndRepeat()))) {
								returnList.get(i).add(event);
							}
						}
					}
				}
			}
		}
		
		return returnList;
	}
	
	//
	// GUI Controller manages time so this provides access to the viewed date
	public LocalDate getViewingDate() {
		return date.getViewingDate();
	}
	
	//
	// GUI Controller sends data to database and adds it to view
	// EventGO event will NOT have id set
	public void addEvent(EventGO event) {
		event.setID(controller.addEventToDatabase(event));
		eventList.add(event);
		calendarView.updateEvents();
		updatePane();
	}
	
	//
	// GUI Controller updates data in database and edits it in view
	// EventGO event will have id set
	public void updateEvent(EventGO event) {
		controller.updateEventInDatabase(event);
		calendarView.updateEvents();
		updatePane();
	}
	
	//
	// GUI Controller sends removal request to database and removes it from view
	public void deleteEvent(EventGO event) {
		controller.deleteEventFromDatabase(event.getID());
		eventList.remove(event);
		calendarView.updateEvents();
		updatePane();
	}
	
	
	//
	// get current username
	public String getUsername() {
		return username;
	}
	
	//
	// Displays popup when viewing day
	public void showDayViewPopup(LocalDate date) {
		new DayViewDialog(date, primaryStage, this);
	}
	
}
