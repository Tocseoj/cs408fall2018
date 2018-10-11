package gui;

import java.time.LocalDate;

import javafx.scene.layout.Pane;

public interface CalendarViews {
		
	Pane getCalendarView(LocalDate date);
	
//	void removeEventFromView(EventGO e);
//	
//	void addEventToView(EventGO e);
	
	void updateEvents();
		
}
