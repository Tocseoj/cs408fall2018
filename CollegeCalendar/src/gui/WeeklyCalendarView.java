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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeeklyCalendarView {

	// Class variables
	private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35); // 35 anchorpanenodes, each one represents a day
	
	
	private VBox view;
	private Text calendarView;
	
	private Date date; // today's date
	
	private int day; // todays day
	
	private int month; // the current month
	
	private int year; // the current year
	
	private YearMonth currentYearAndMonth;
	
	private Text weeklyCalendarTitle; // the title of the weekly calendar
										// currently being viewed
	
	
	
	/*
	 * Constructor
	 */
	public WeeklyCalendarView(YearMonth yearAndMonth) {
		this.currentYearAndMonth = yearAndMonth;
		
		
		GridPane weeklyCalendar = new GridPane();
		double prefWidth = 600;
		double prefHeight = 200;
		weeklyCalendar.setPrefSize(prefWidth, prefHeight);
		
		weeklyCalendar.setGridLinesVisible(true);
		
		// add all the days/nodes for the monthly calendar
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				AnchorPaneNode currDay = new AnchorPaneNode();
				currDay.setPrefSize(200,200);
				weeklyCalendar.add(currDay, j, i);
				allCalendarDays.add(currDay);
			}
		}
		
		GridPane dayNamesGrid = new GridPane();
		dayNamesGrid.setPrefWidth(600);
		Integer col = 0; // track the column we're editing
		
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
			dayNamesGrid.add(pane, col, 0); // 0 because we want the first row
											// to have the day labels
			col++;
		}

		weeklyCalendarTitle = new Text();
		
		Button userOptions = new Button("User Options");
		
		// Create a button to go back a month
		Button prevWeek = new Button("Prev Week");
		// assign the action to go back a month to this button
		prevWeek.setOnAction(e -> viewPreviousWeek());
		
		// do the same for a next month button
		Button nextWeek = new Button("Next Week");
		// assign onaction
		nextWeek.setOnAction(e -> viewNextWeek());
		
		
		HBox title = new HBox(prevWeek, weeklyCalendarTitle, nextWeek);
		
		title.setAlignment(Pos.BASELINE_CENTER); // center align the title bar
		
		
		// create a new date object, with today's date
		this.date = new Date();
		// convert to LocalDate to use the getters
		LocalDate localDate = this.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
//		int day = cal.get(Calendar.DAY_OF_WEEK); // get todays day
		int day   = localDate.getDayOfMonth();
		
		// set class variable to today's day
		this.day = day;
		
		// get the current month
		int month = localDate.getMonthValue();
		// set the class variable
		this.month = month;
		
		int year = localDate.getYear();
		this.year = year;
		

		
		
//		System.out.println("val of date is: " + day);
		
		computeCalendar(this.year, this.month, this.day);
		
		view = new VBox(title, dayNamesGrid, weeklyCalendar); // the calendar view
		
		
	}
	
	/*
	 * Computes the day numbers for the weekly calendar
	 */
	private void computeCalendar(int year, int month, int day) {
		// create a var to hold today's date
		LocalDate currDate = LocalDate.of(year, month, day);

		// need to compute the first sunday before the currDate, calendar starts on sunday
		while (!currDate.getDayOfWeek().toString().equals("SUNDAY")) {
			// subtract a day from currDate
			currDate = currDate.minusDays(1);
		}

		// currDate should be the first Sunday of the week that the user requested

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
		String currMonth = String.valueOf(month);
		currMonth = currMonth.substring(0, 1).toUpperCase() + currMonth.substring(1).toLowerCase();
		String currYear = String.valueOf(year);
		weeklyCalendarTitle.setText("Week of " + currMonth + "/" + day + "/" + currYear);
	}


	/*
	 * Increments the date by a week
	 */
	private void viewNextWeek() {
		// TODO Auto-generated method stub
		int numDays = 7; // incrementing by one week
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date); // set the calendar to the current date
		cal.add(Calendar.DAY_OF_YEAR, numDays); // increment the calendar by a week
		this.date = cal.getTime(); // get the incremented time, update the date of the class
		// compute the local date
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int day   = localDate.getDayOfMonth();
		
		// set class variable to today's day
		this.day = day;
		
		// get the current month
		int month = localDate.getMonthValue();
		// set the class variable
		this.month = month;
		
		int year = localDate.getYear();
		this.year = year;
		
		
//		currentYearAndMonth = currentYearAndMonth.plusMonths(1); // increment the month
		computeCalendar(this.year, this.month, this.day); // re-compute the days of the calendar
	}
	
	
	
	/*
	 * Rewind the weekly calendar view by a week
	 */
	private void viewPreviousWeek() {
		int numDays = -7; // incrementing by one week
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date); // set the calendar to the current date
		cal.add(Calendar.DAY_OF_YEAR, numDays); // increment the calendar by a week
		this.date = cal.getTime(); // get the incremented time, update the date of the class
		// compute the local date
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int day   = localDate.getDayOfMonth();

		// set class variable to today's day
		this.day = day;

		// get the current month
		int month = localDate.getMonthValue();
		// set the class variable
		this.month = month;

		int year = localDate.getYear();
		this.year = year;
//		currentYearAndMonth = currentYearAndMonth.minusMonths(1); // increment the month
		computeCalendar(this.year, this.month, this.day); // re-compute the days of the calendar
	}
	
	
	/*
	 * Getter for the view
	 */
	public VBox getView() {
		return this.view;
	}
	
	/*
	 * Getter for all the calendar days
	 */
	public ArrayList<AnchorPaneNode> getAllCalendarDays() {
		return this.allCalendarDays;
	}
	
	/*
	 * Setter for calendar days
	 */
	public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
		this.allCalendarDays = allCalendarDays;
	}
	
}
