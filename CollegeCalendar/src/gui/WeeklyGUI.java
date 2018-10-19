package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class WeeklyGUI implements CalendarViews {
	
	private GUIController guiController;
	
	private ArrayList<ArrayList<EventGO>> viewingEvents;
	
	public WeeklyGUI(GUIController guiController) {
		this.guiController = guiController;
		
		viewingEvents = guiController.getWeekEvents(guiController.getViewingDate());
	}
	
	@Override
	public Pane getCalendarView(LocalDate date) {
		// TODO: implement weekly view
		VBox container = new VBox();
		Label monthYearLabel = new Label(getDateFormat(date));			// Month / Year label for given date
		HBox weekView = new HBox();									// Grid for calendar/weekly view
		
		int dayOfWeek = date.getDayOfWeek().getValue();
		dayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek;
		
		for (int i = 0; i < 7; i++) {
			LocalDate dayOfMonth = date.minusDays(dayOfWeek - i);
			
			VBox day = new VBox();
			Button dayButton = new Button(dayOfMonth.getDayOfMonth() + "");
			dayButton.setUserData(dayOfMonth);
			dayButton.setOnAction(this::dayButton);
			
			ArrayList<EventGO> eventList = viewingEvents.get(i);
			
			for (EventGO event : eventList) {
				Button e = new Button(event.getTitle() + "\n" + event.getTime().toString());
				
				e.setUserData(event);
				e.setOnAction(this::viewEventPopup);
				
				day.getChildren().add(e);
			}
			
			VBox dayContainer = new VBox();
			ScrollPane sp = new ScrollPane();
			sp.setContent(day);
			VBox.setVgrow(sp, Priority.ALWAYS);
			
			dayContainer.getChildren().addAll(dayButton, sp);
			
			HBox.setHgrow(dayContainer, Priority.ALWAYS);
			
			weekView.getChildren().add(dayContainer);
		
		}
		
		//
		// Complete and Return pane 
		//
		
		// Set grow
		VBox.setVgrow(weekView, Priority.ALWAYS);
		
		
		container.getChildren().addAll(monthYearLabel, weekView);
		
		return container;
	}

	private String getDateFormat(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MMMM yyyy 'Week' W"));
	}

	@Override
	public void updateEvents() {
		viewingEvents = guiController.getWeekEvents(guiController.getViewingDate());
	}

	private void viewEventPopup(ActionEvent event) {
		guiController.showEventPopup((EventGO)((Button)(event.getSource())).getUserData());
	}
	private void dayButton(ActionEvent event) {
		guiController.showDayViewPopup((LocalDate)((Button)(event.getSource())).getUserData());
	}
}
