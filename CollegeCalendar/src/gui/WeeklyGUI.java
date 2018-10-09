package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.Controller;
import javafx.scene.layout.Pane;

public class WeeklyGUI implements CalendarViews {
	
	private Controller controller;
	
	public WeeklyGUI(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public Pane getCalendarView(LocalDate date, String username) {
		// TODO: implement weekly view
		System.out.println("Replace with weekly");
		return new Pane();
	}

	@Override
	public String getDateFormat(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MMMM yyyy 'Week' W"));
	}

	@Override
	public void removeEventFromView(EventGO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEventToView(EventGO e) {
		// TODO Auto-generated method stub
		
	}

}
