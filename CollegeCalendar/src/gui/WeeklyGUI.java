package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.Controller;
import javafx.scene.layout.Pane;

public class WeeklyGUI implements CalendarViews {
	
	private Controller controller;
	private GUIController guiController;
	
	public WeeklyGUI(Controller controller, GUIController guiController) {
		this.controller = controller;
		this.guiController = guiController;
	}
	
	@Override
	public Pane getCalendarView(LocalDate date) {
		// TODO: implement weekly view
		System.out.println("Replace with weekly");
		return new Pane();
	}

	private String getDateFormat(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MMMM yyyy 'Week' W"));
	}

//	@Override
//	public void removeEventFromView(EventGO e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addEventToView(EventGO e) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void updateEvents() {
		// TODO Auto-generated method stub
		
	}

}
