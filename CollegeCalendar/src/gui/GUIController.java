package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

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

	private static PopUpController	popUpController;
	
	private Controller controller;
	private ArrayList<EventGO> eventList;
	private ArrayList<ContactGO> contactList;

	public GUIController (Stage primaryStage, StackPane calendarPane) {

		this.primaryStage = primaryStage;
		this.calendarPane = calendarPane;

		username = "tester";

		date = new DateAndTimeManager();

		controller = new Controller();
		eventList = controller.getAllEvents(username);
		contactList = controller.getAllContacts(username);
		
		checkAndAddContactEvents();
		
		calendarView = new MonthlyGUI(this);

		popUpController = new PopUpController();


		updatePane();

	}

	//
	// Refresh the calendar view from CalendarViews interface
	public void updatePane() {
		calendarPane.getChildren().remove(dynamicPane);
		dynamicPane = calendarView.getCalendarView(date.getViewingDate());
		calendarPane.getChildren().add(dynamicPane);
	}

	public void checkAndAddContactEvents() {
		for(ContactGO cgo : contactList) {
			boolean check = true;
			LocalDate start = LocalDate.now();
			LocalDate finish = start.plusDays(15);
			ArrayList<ArrayList<EventGO>> all = getEvents(start, finish, 15);
			for(ArrayList<EventGO> al : all) {
				for(EventGO e : al) {
					String compare = "Contact " + cgo.getContactName();
					if(e.getTitle().equals(compare)) {
						check = false;
					}
				}
			}
			if(check) {
				addContactEvent(cgo);
			}else {
				check = true;
			}
		}
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
	public void updateEvent(EventGO event, EventGO old) {
		controller.updateEventInDatabase(event);
		int index = eventList.indexOf(old);
		eventList.remove(index);
		eventList.add(index, event);
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
	
	public void addContactEvent(ContactGO c) {
		Random random = new Random();
        int rand = random.nextInt(14 + 1);
        LocalDate randDate = LocalDate.now().plusDays(rand);
        Duration d = Duration.ofMinutes(15);
        LocalDate date = suggestDateForContact(d,randDate);
        LocalTime time = suggestTime(date, d);
        String title = "Contact " + c.getContactName();
        EventGO e = new EventGO(title, username, d, date, time);
        controller.addEventToDatabase(e);
        if(e.getID() != "") {
        	eventList.add(e);
        	calendarView.updateEvents();
			updatePane();
        }
	}
	
	public void deleteContactEventFromDbAndLocal(String title, String userName) {
		for(EventGO e : eventList) {
			if(e.getTitle().equals(title) && e.getUserName().equals(userName)) {
				controller.deleteEventFromDatabase(e.getID());
			}
		}
		eventList = controller.getAllEvents(username);
		calendarView.updateEvents();
		updatePane();
	}

	public LocalDate suggestDate(Duration duration) {
		LocalDate start = LocalDate.now();
		LocalDate finish = start;
		long plannedHours = 0;
		long maxHours = 8 - duration.toHours();
		ArrayList<EventGO> eventsInDay;
		LocalDate result = start;
		do {
			plannedHours = 0;
			result = start;
			eventsInDay = getEvents(start, finish, 1).get(0);
			for(EventGO event : eventsInDay) {
				plannedHours = plannedHours + event.getDuration().toHours();
			}
			start = start.plusDays(1);
			finish = start;
		}while(plannedHours > maxHours);
		return result;
	}
	
	public LocalDate suggestDateForContact(Duration duration, LocalDate date) {
		LocalDate start = date;
		LocalDate finish = start;
		long plannedHours = 0;
		long maxHours = 10 - duration.toHours();
		ArrayList<EventGO> eventsInDay;
		LocalDate result = start;
		do {
			plannedHours = 0;
			result = start;
			eventsInDay = getEvents(start, finish, 1).get(0);
			for(EventGO event : eventsInDay) {
				plannedHours = plannedHours + event.getDuration().toHours();
			}
			start = start.plusDays(1);
			finish = start;
		}while(plannedHours > maxHours);
		return result;
	}

	public LocalTime suggestTime(LocalDate date, Duration duration) {
		LocalDate nowDate = LocalDate.now();
		ArrayList<EventGO> eventsInDay = getEvents(date, date, 1).get(0);
		if(eventsInDay.size() == 0) {
			LocalTime noOtherEvents = LocalTime.parse("08:00");
			while(noOtherEvents.isBefore(LocalTime.parse("21:00"))) {
				if(!date.equals(nowDate) || noOtherEvents.isAfter(LocalTime.now())) {
					return noOtherEvents;
				}
				noOtherEvents = noOtherEvents.plusHours(1);
			}
			return suggestTime(date.plusDays(1), duration);
		}
		EventGO earliest = eventsInDay.get(0);
		for(EventGO event : eventsInDay) {
			if(event.getTime().isBefore(earliest.getTime())) {
				earliest = event;
			}
		}
		LocalTime earliestTime = earliest.getTime().minus(duration);
		if(earliestTime.getHour() >= 8) {
			if(!date.equals(nowDate) || earliestTime.isAfter(LocalTime.now())) {
				return earliestTime;
			}
		}
		if(eventsInDay.size() > 1) {
			for(EventGO event : eventsInDay) {
				EventGO closest = event;
				for(EventGO event1 : eventsInDay) {
					if(event1.getTime().isAfter(event.getTime())) {
						if(closest.getTime().compareTo(event.getTime()) == 0) {
							closest = event1;
						}else if(closest.getTime().compareTo(event1.getTime()) > 0) {
							closest = event1;
						}
					}
				}
				LocalTime endOfEvent = event.getTime().plus(event.getDuration());

				if(endOfEvent.until(closest.getTime(), ChronoUnit.MINUTES) >= duration.toMinutes()) {
					if(!date.equals(nowDate) || endOfEvent.isAfter(LocalTime.now())) {
						return endOfEvent;
					}
				}
			}
		}
		EventGO latest = eventsInDay.get(0);
		for(EventGO event : eventsInDay) {
			if(event.getTime().isAfter(latest.getTime())) {
				latest = event;
			}
		}
		LocalTime latestTime = latest.getTime().plus(latest.getDuration());
		if(!date.equals(nowDate) || latestTime.isAfter(LocalTime.now())) {
			return latestTime;
		}
		return suggestTime(date.plusDays(1), duration);
	}

	//
	// Helper method to get all events in same week of specified day
	public ArrayList<ArrayList<EventGO>> getWeekEvents(LocalDate day) {

		int length = 7;
		int dayOfWeek = day.getDayOfWeek().getValue();
		dayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek;
		LocalDate start = day.minusDays(dayOfWeek);
		LocalDate finish = day.plusDays(6 - dayOfWeek);

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
				try {
					returnList.get((int)start.until(event.getDate(), ChronoUnit.DAYS)).add(event);
				} catch (IndexOutOfBoundsException e) {
					// WeeklyView was bugged, but now fixed
					// However this will prevent crashes
					// TODO
					System.err.println((int)start.until(event.getDate(), ChronoUnit.DAYS) + " is not in length of returnList");
				}
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
	
	public void addContactButton() {
		new ContactDialog(primaryStage, null, this);
	}
	
	public void addToContactList(ContactGO cgo) {
		contactList.add(cgo);
	}
	
	public String removeFromContactList(String userName, String contactName) {
		for(ContactGO c: contactList) {
			if(c.getContactName().equals(contactName) && c.getUserName().equals(userName)) {
				String sid = c.getID();
				contactList.remove(c);
				return sid;
			}
		}
		return null;
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
	 * PopUp checker for all events. Handles prior events,
	 * current reminders and completed events
	 */
	public void handlePopUps() {
		LocalDateTime currTime = date.getCurrentDateTime();
		LocalTime tmp = null;
		LocalDateTime eventCheck = null;
		
		int lastRemind = -1;
		int size = eventList.size();
		for (int i = 0; i < size; i++) 
		{
			/* Prep comparison object for checks */
			tmp = eventList.get(i).getTime();
			eventCheck = eventList.get(i).getDate().atTime(tmp);
			
			/* Handle already completed events */
			if (eventCheck.plusMinutes(eventList.get(i).getDuration().toMinutes())
					.compareTo(currTime) <= 0) {
				if (Boolean.TRUE.equals(eventList.get(i).getAllottedTimeUp())) {
					popUpController.eventCompleted(eventList.get(i));
					/* Update in database */
					eventList.get(i).setCompleted(true);
					eventList.get(i).setAllottedTimeUp(false);
					try {
						controller.updateEventInDatabase(eventList.get(i));
					} catch (NullPointerException e) {
						continue;
					}
				}
				continue;
			}
			
			/* Handle current events */
			int ret, min = currTime.getMinute();
			if ((ret = currTime.compareTo(eventCheck)) >= 0) {
				if (Boolean.TRUE.equals(eventList.get(i).getConstantReminder())) {
					if (lastRemind != min && (min == 00 || min == 15 || min == 30 || min == 45)) {
						popUpController.remindUser(eventList.get(i));
						lastRemind = min;
					}
				}
				continue;
			}
			
			/* Handle prior events */
			if (!eventList.get(i).getNotificationOffset().isNegative()) {
				if (eventCheck.minusMinutes(eventList.get(i)
						.getNotificationOffset().toMinutes())
						.compareTo(currTime) <= 0) {
					popUpController.notifyUpcomingEvent(eventList.get(i));
					eventList.get(i).setNotificationOffset(Duration.ofMinutes(-1));
					try {
						controller.updateEventInDatabase(eventList.get(i));
					} catch (NullPointerException e) {
						continue;
					}
				}
			}
		}
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
