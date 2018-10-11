package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;

import controller.Controller;
import controller.DateAndTimeManager;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*
 * 
 * 		Manages all data that needs to be kept for events and GUI Control
 * 
 */

public class GUIController {

	private Stage primaryStage;

	private CalendarViews calendarView;
	private DateAndTimeManager date;
	private String username;
	private Pane dynamicPane;

	private StackPane calendarPane;

	private Controller controller;
	private ArrayList<EventGO> eventList;

	public GUIController (Stage primaryStage, StackPane calendarPane) {

		this.primaryStage = primaryStage;
		this.calendarPane = calendarPane;

		username = "tester";

		date = new DateAndTimeManager();

		controller = new Controller();
		eventList = controller.getAllEvents(username);

		calendarView = new MonthlyGUI(this);

		updatePane();

	}

	//
	// Refresh the calendar view from CalendarViews interface
	private void updatePane() {
		calendarPane.getChildren().remove(dynamicPane);
		dynamicPane = calendarView.getCalendarView(date.getViewingDate());
		calendarPane.getChildren().add(dynamicPane);
	}
	
	// GUI Controller sends data to database and adds it to view
	// EventGO event will NOT have id set
	public void addEvent(EventGO event) {
		event.setID(controller.addEventToDatabase(event));
		eventList.add(event);
		calendarView.updateEvents();
		updatePane();
	}

	// GUI Controller updates data in database and edits it in view
	// EventGO event will have id set
	public void updateEvent(EventGO event) {
		controller.updateEventInDatabase(event);
		calendarView.updateEvents();
		updatePane();
	}

	// GUI Controller sends removal request to database and removes it from view
	public void deleteEvent(EventGO event) {
		controller.deleteEventFromDatabase(event.getID());
		eventList.remove(event);
		calendarView.updateEvents();
		updatePane();
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

	
	/*
	 * Called by button handlers 
	 */
	
	// Called by switch view dropdown
	// creates new selected view and updates
	public void switchView(String viewName) {
		if (viewName.equals("Calendar View") || viewName.equals("Monthly View")) {
			if (calendarView instanceof MonthlyGUI) {
				return;
			}
			calendarView = new MonthlyGUI(this);
			updatePane();
		}
		else if (viewName.equals("Weekly View")) {
			if (calendarView instanceof WeeklyGUI) {
				return;
			}
			calendarView = new WeeklyGUI(this);
			updatePane();
		}
	}
	// Called by left arrow button
	// sets viewing date minus a time amount
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
	// Called by right arrow button
	// sets viewing date plus a time amount
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
	// Called by "Today" button
	// updates view to current day
	public void todayButton() {
		date.setViewingDate(DateAndTimeManager.getCurrentDate());
		calendarView.updateEvents();
		updatePane();
	}

	// Called when press "Add Event" button
	// creates an empty EventDialog
	public void addEventButton() {
		new EventDialog(primaryStage, null, this);
	}

	
	/*
	 * Dialog Creation
	 */
	public void showUsernamePopup() {
		new UsernameDialog(primaryStage, this);
	}
	// Displays popup when viewing day
	public void showDayViewPopup(LocalDate date) {
		new DayViewDialog(date, primaryStage, this);
	}
	// Called when clicking on an event
	public void showEventPopup(EventGO event) {
		new EventDialog(primaryStage, event, this);
	}


	/*
	 * Getters/ Setters
	 */
	public void setUsername(String username) {
		this.username = username;

		// Need to load events for new username
		eventList = controller.getAllEvents(this.username);
		calendarView.updateEvents();
		updatePane();
	}
	public void changePrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public String getUsername() {
		return username;
	}
	public Pane getDynamicPane() {
		return dynamicPane;
	}
	// GUI Controller manages time so this provides access to the viewed date
	public LocalDate getViewingDate() {
		return date.getViewingDate();
	}
}
