package gui;

import java.time.LocalDate;

import controller.Controller;
import controller.DateAndTimeManager;
import javafx.scene.layout.Pane;

public interface CalendarViews {
	
	DateAndTimeManager dateTime = new DateAndTimeManager();
	
	Pane getCalendarView(LocalDate date, String username);
	
	void removeEventFromView(EventGO e);
	
	void addEventToView(EventGO e);
	
	String getDateFormat(LocalDate date);
	
}
