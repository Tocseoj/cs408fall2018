package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.layout.Pane;

public class WeeklyGUI implements CalendarViews {
	
	private GUIController guiController;
	
	public WeeklyGUI(GUIController guiController) {
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
