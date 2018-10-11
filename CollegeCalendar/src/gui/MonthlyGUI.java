package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MonthlyGUI implements CalendarViews {
		
	private GUIController guiController;
	
	private ArrayList<ArrayList<EventGO>> viewingEvents;
	
	private VBox[][] dayBlocks;
	
	private GridPane calendarGrid;
	
	public MonthlyGUI(GUIController guiController) {
		this.guiController = guiController;
		
		viewingEvents = guiController.getMonthEvents(guiController.getViewingDate());
	}
	
	@Override
	public void updateEvents() {
		// TODO Auto-generated method stub
		viewingEvents = guiController.getMonthEvents(guiController.getViewingDate());
	}

	@Override
	public Pane getCalendarView(LocalDate date) {
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
		dayOfFirst = dayOfFirst == 7 ? 0 : dayOfFirst;
		int rowCount = (int)(Math.ceil((date.lengthOfMonth() + dayOfFirst) / 7.0));
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
		dayBlocks = new VBox[columnCount][rowCount];
		
		for (int c = 0; c < columnCount; c++) {
			for (int r = 0; r < rowCount; r++) {
				int dayOfMonth = (r * 7) + (c + 1) - dayOfFirst;				
				
				VBox day = new VBox();
				
//				Rectangle rect = new Rectangle(200, 150);
//				rect.heightProperty().bind(day.heightProperty());
//				rect.widthProperty().bind(day.widthProperty());
//				day.setClip(rect);
				
				Button dayButton = new Button(dayOfMonth + "");
				dayButton.setUserData(date.withDayOfMonth(1).plusDays(dayOfMonth - 1));
				dayButton.setOnAction(this::dayButton);
				
				dayBlocks[c][r] = day;
				
				ArrayList<EventGO> eventList = new ArrayList<>();
				
				if (dayOfMonth < 1 || dayOfMonth > date.lengthOfMonth()) {					
//					int otherDay = date.withDayOfMonth(1).plusDays(dayOfMonth - 1).getDayOfMonth();
//					dayButton.setText(otherDay + "");
//					day.getStyleClass().add("other-month");
//					eventList = new ArrayList<EventGO>();
//					if (dayOfMonth <= 0) {
//						viewingEvents.add(0, eventList);
//					} else {
//						viewingEvents.add(eventList);
//					}	
					continue;
				} else {
					eventList = viewingEvents.get(dayOfMonth - 1);
				}
				
//				day.getChildren().add(dayButton);
				
				for (EventGO event : eventList) {
					Button e = new Button(event.getTitle());
					
					e.setUserData(event);
					e.setOnAction(this::viewEventPopup);
					
					day.getChildren().add(e);
				}
				
				VBox dayContainer = new VBox();
				ScrollPane sp = new ScrollPane();
				sp.setContent(day);
				VBox.setVgrow(sp, Priority.ALWAYS);
				
				dayContainer.getChildren().addAll(dayButton, sp);
				
				calendarGrid.add(dayContainer, c, r);
				
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

	private String getDateFormat(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
	}

//	@Override
//	public void removeEventFromView(EventGO e) {
//		// TODO Auto-generated method stub
//		int r, c;
//		
//		if (guiController.getViewingDate().getMonth().equals(e.getDate().getMonth())) {
//			
//			int dayOfFirst = e.getDate().withDayOfMonth(1).getDayOfWeek().getValue();
//			dayOfFirst = dayOfFirst == 7 ? 0 : dayOfFirst;
//
//			int day = e.getDate().getDayOfMonth();
//			r = (day + dayOfFirst) / 7;
//			c = e.getDate().getDayOfWeek().getValue() == 7 ? 0 : e.getDate().getDayOfWeek().getValue();	
//			
//		} else {
//			r = 0;
//			c = 0;
//		}
//		
//		VBox dayContainer = dayBlocks[c][r];
//		
//		Node toRemove = null;
//		for (Node b : dayContainer.getChildren()) {
//			EventGO event = (EventGO)b.getUserData();
//			if (event.equals(e)) {
//				toRemove = b;
//				break;
//			}
//		}
//		if (toRemove != null) {
//			dayContainer.getChildren().remove(toRemove);
//		}
////		dayContainer.getChildren().remove(e);
//		
////		if (dateTime.getViewingDate().getMonth().equals(e.getDate().getMonth())) {
////			
//////			viewingEvents.get(e.getDate().getDayOfMonth()).remove(e);
////		} else {
////			
////		}
//	}

//	@Override
//	public void addEventToView(EventGO e) {
//
//		int r, c;
//		
//		if (guiController.getViewingDate().getMonth().equals(e.getDate().getMonth())) {
//			
//			int dayOfFirst = e.getDate().withDayOfMonth(1).getDayOfWeek().getValue();
//			dayOfFirst = dayOfFirst == 7 ? 0 : dayOfFirst;
//
//			int day = e.getDate().getDayOfMonth();
//			r = (day + dayOfFirst) / 7;
//			c = e.getDate().getDayOfWeek().getValue() == 7 ? 0 : e.getDate().getDayOfWeek().getValue();	
//			
//		} else {
//			r = 0;
//			c = 0;
//		}
//		
////		System.out.println("c:" + c + " r:" + r);
//		VBox dayContainer = dayBlocks[c][r];
//		Button b = new Button(e.getTitle());
//		b.setUserData(e);
//		b.setOnAction(this::viewEventPopup);
//		
//		Boolean added = false;
//		
//		for (int i = 0; i < dayContainer.getChildren().size(); i++) {
//			LocalTime dt = ((EventGO)(dayContainer.getChildren().get(i).getUserData())).getTime();
//			if (dt.isAfter(e.getTime())) {
//				dayContainer.getChildren().add(i, b);
//				added = true;
//				break;
//			}
//		}
//		if (!added) {
//			dayContainer.getChildren().add(b);
//		}
//	}
	
	private void viewEventPopup(ActionEvent event) {
		guiController.showEventPopup((EventGO)((Button)(event.getSource())).getUserData());
	}
	private void dayButton(ActionEvent event) {
		guiController.showDayViewPopup((LocalDate)((Button)(event.getSource())).getUserData());
	}

}
