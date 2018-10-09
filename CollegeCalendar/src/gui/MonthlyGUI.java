package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MonthlyGUI implements CalendarViews {
	
	private Controller controller;
	
	private ArrayList<ArrayList<EventGO>> viewingEvents;
	
	private GridPane calendarGrid;
	
	public MonthlyGUI(Controller controller) {
		this.controller = controller;
	}

	@Override
	public Pane getCalendarView(LocalDate date, String username) {
		//
		// Setup Container Pane
		//
		VBox container = new VBox();									// Container for label and gridpane
		Label monthYearLabel = new Label(getDateFormat(date));			// Month / Year label for given date
		calendarGrid = new GridPane();							// Grid for calendar/monthly view
		
		calendarGrid.setGridLinesVisible(true);//DEBUG
		
		//
		// Setup GridPane for calendar
		//
		ArrayList<ColumnConstraints> 	columnList 	= new ArrayList<>();
		ArrayList<RowConstraints> 		rowList 	= new ArrayList<>();
		
		int columnCount = 7;
		for (int c = 0; c < columnCount; c++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100);
			cc.setFillWidth(true); // ask nodes to fill space for column
			columnList.add(cc);
		}
		calendarGrid.getColumnConstraints().addAll(columnList);
				
		int dayOfFirst = date.withDayOfMonth(1).getDayOfWeek().getValue();
		int rowCount = (int)(Math.ceil((date.lengthOfMonth() + dayOfFirst) / 7.0));
		dayOfFirst = dayOfFirst == 7 ? 0 : dayOfFirst;
		for (int r = 0; r < rowCount; r++) {
			RowConstraints rc = new RowConstraints();
			rc.setPercentHeight(100);
			rc.setFillHeight(true); // ask nodes to fill space for row
			rowList.add(rc);
		}
		calendarGrid.getRowConstraints().addAll(rowList);
		
		//
		// Populate GridPane
		//
		ArrayList<ArrayList<EventGO>> monthList = new ArrayList<>();
		if (username != null) {
			monthList = controller.getEventsOnMonth(username, date);
			viewingEvents = monthList;
		}
		
		for (int c = 0; c < columnCount; c++) {
			for (int r = 0; r < rowCount; r++) {
				int dayOfMonth = (r * 7) + (c + 1) - dayOfFirst;				
				
				VBox day = new VBox();
				Button dayButton = new Button(dayOfMonth + "");
				
				ArrayList<EventGO> eventList = new ArrayList<>();
				
				if (dayOfMonth < 1 || dayOfMonth > date.lengthOfMonth()) {
					int otherDay = date.withDayOfMonth(1).plusDays(dayOfMonth - 1).getDayOfMonth();
					dayButton.setText(otherDay + "");
					day.getStyleClass().add("other-month");
					if (username != null) {
						eventList = controller.getEventsOnDay(username, date.withDayOfMonth(1).plusDays(dayOfMonth - 1));
						if (dayOfMonth <= 0) {
							viewingEvents.add(0, eventList);
						} else {
							viewingEvents.add(eventList);
						}
					}
				} else {
					if (username != null) {
						eventList = monthList.get(dayOfMonth - 1);
					}
				}
				
				day.getChildren().add(dayButton);
				
				for (EventGO event : eventList) {
					Button e = new Button(event.getTitle());
					
					day.getChildren().add(e);
				}
				
				calendarGrid.add(day, c, r);
				
			}
		}
		
		//
		// Complete and Return pane 
		//
		
		// Set grow
		VBox.setVgrow(calendarGrid, Priority.ALWAYS);
		HBox.setHgrow(calendarGrid, Priority.ALWAYS);
		
		container.getChildren().addAll(monthYearLabel, calendarGrid);
		
		return container;
	}

	@Override
	public String getDateFormat(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
	}

	@Override
	public void removeEventFromView(EventGO e) {
		// TODO Auto-generated method stub
		if (dateTime.getViewingDate().getMonth().equals(e.getDate().getMonth())) {
			calendarGrid.get
			viewingEvents.get(e.getDate().getDayOfMonth()).remove(e);
		} else {
			
		}
	}

	@Override
	public void addEventToView(EventGO e) {
		// TODO Auto-generated method stub
		
	}

}
