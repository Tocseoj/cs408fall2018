package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class MonthlyCalendarView {
private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35); // 35 anchorpanenodes, each one represents a day
	
	
	private VBox view;
	private Text calendarView;
	

	
	private YearMonth currentYearAndMonth;
	
	private Text monthlyCalendarTitle; // the title of the monthly calendar
										// currently being viewed
	
	
	public MonthlyCalendarView(YearMonth currMonth) {
		this.currentYearAndMonth = currMonth;
		
		//Make a grid pane for the calendar
		GridPane monthlyCalendar = new GridPane();
		monthlyCalendar.setPrefSize(600, 400);
		
		monthlyCalendar.setGridLinesVisible(true);
		
		// add all the days/nodes for the monthly calendar
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				AnchorPaneNode currDay = new AnchorPaneNode();
				currDay.setPrefSize(200,200);
				monthlyCalendar.add(currDay, j, i);
				allCalendarDays.add(currDay);
			}
		}
		
		GridPane dayNamesGrid = new GridPane();
		dayNamesGrid.setPrefWidth(600);
		Integer col = 0;
		
		Text[] dayLabels = new Text[] {
				new Text("Sunday"),
				new Text("Monday"),
				new Text("Tuesday"),
				new Text("Wednesday"),
				new Text("Thursday"),
				new Text("Friday"),
				new Text("Saturday"),
		};
		
		for (Text currDayName : dayLabels) {
			AnchorPane pane = new AnchorPane();
			pane.setPrefSize(200, 10);
			
			double val = 5.0;
			pane.setBottomAnchor(currDayName, val);
			pane.getChildren().add(currDayName);
			dayNamesGrid.add(pane, col++, 0); // 0 because we want the first row
											// to have the day labels
			
		}
		
		monthlyCalendarTitle = new Text();
		
		
		Button userOptions = new Button("User Options");
		
		// Create a button to go back a month
		Button prevMonth = new Button("Prev Month");
		// assign the action to go back a month to this button
		prevMonth.setOnAction(e -> viewPreviousMonth());
		
		// do the same for a next month button
		Button nextMonth = new Button("Next Month");
		// assign onaction
		nextMonth.setOnAction(e -> viewNextMonth());
		
		
		HBox title = new HBox(prevMonth, monthlyCalendarTitle, nextMonth);
		
		title.setAlignment(Pos.BASELINE_CENTER); // center align the title bar
		
		computeCalendar(currentYearAndMonth);
		
		view = new VBox(title, dayNamesGrid, monthlyCalendar); // the calendar view
	}
	
	
	
	private void computeCalendar(YearMonth yearAndMonth) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
			// Get the date we want to start with on the calendar
			LocalDate currDate = LocalDate.of(yearAndMonth.getYear(), yearAndMonth.getMonthValue(), 1);
			
			// need to compute the first sunday before the currDate
			while (!currDate.getDayOfWeek().toString().equals("SUNDAY")) {
				// subtract a day from currDate
				currDate = currDate.minusDays(1);
			}
			
			
			// compute the day numbers for the calendar
			for (AnchorPaneNode node : allCalendarDays) {
				if (node.getChildren().size() != 0) {
					node.getChildren().remove(0);
				}
				
				Text text = new Text(String.valueOf(currDate.getDayOfMonth()));
				node.setDate(currDate); // set the date of the node
				
				double anchorVal = 5.0;
				node.setTopAnchor(text, anchorVal);
				node.setLeftAnchor(text,  anchorVal);
				node.getChildren().add(text);
				
				
				// increment the currDate by 1
				currDate = currDate.plusDays(1);
			}
			
			
			// compute the title of the calendar
			String currMonth = yearAndMonth.getMonth().toString();
			currMonth = currMonth.substring(0, 1).toUpperCase() + currMonth.substring(1).toLowerCase();
			String currYear = String.valueOf(yearAndMonth.getYear());
			monthlyCalendarTitle.setText(currMonth + ", " + currYear);
	}



	private void viewPreviousMonth() {
		// TODO Auto-generated method stub
		currentYearAndMonth = currentYearAndMonth.minusMonths(1); // increment the month
		computeCalendar(currentYearAndMonth); // re-compute the days of the calendar
	}
	
	
	private void viewNextMonth()
	{
		currentYearAndMonth = currentYearAndMonth.plusMonths(1); // increment the month
		computeCalendar(currentYearAndMonth); // re-compute the days of the calendar
	}
	
	
	
	/*
	 * Getter for the view
	 */
	public VBox getView() {
		return this.view;
	}
	
	/*
	 * Getter for all the calendar daus
	 */
	public ArrayList<AnchorPaneNode> getAllCalendarDays() {
		return this.allCalendarDays;
	}
	
	/*
	 * 
	 */
	public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
		this.allCalendarDays = allCalendarDays;
	}
}
