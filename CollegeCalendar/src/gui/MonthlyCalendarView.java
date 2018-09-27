package gui;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MonthlyCalendarView {
	private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35); // 35 anchorpanenodes, each one represents a day
	
	private Controller ic = new Controller();
	private ArrayList<EventGO> eventList;
	private ArrayList<Button> buttonList;
	private VBox view;
	private Text calendarView;
	
	private String userName = "testUser";
	

	
	private YearMonth currentYearAndMonth;
	
	private Text monthlyCalendarTitle; // the title of the monthly calendar
										// currently being viewed
	
	
	Button userOptions;
	
	Button viewWeekly;
	
	
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
		this.userOptions = userOptions;
		
		// Create a button to go back a month
		Button prevMonth = new Button("Prev Month");
		// assign the action to go back a month to this button
		prevMonth.setOnAction(e -> viewPreviousMonth());
		
		// do the same for a next month button
		Button nextMonth = new Button("Next Month");
		// assign onaction
		nextMonth.setOnAction(e -> viewNextMonth());
		
		
		Button viewWeekly = new Button("Weekly View");
		this.viewWeekly = viewWeekly;
//		viewWeekly.setOnAction(e -> viewWeekly());
		
		
		HBox title = new HBox(userOptions, prevMonth, monthlyCalendarTitle, nextMonth, viewWeekly);
		
		title.setAlignment(Pos.BASELINE_CENTER); // center align the title bar
		
		computeCalendar(currentYearAndMonth);
		
		view = new VBox(title, dayNamesGrid, monthlyCalendar); // the calendar view
	}
	
	
	
	
	
	/*
	 * Getter for the user options button
	 */
	public Button getUserOptionsButton() {
		return this.userOptions;
	}
	
	
	/*
	 * Getter for the viewWeekly button
	 */
	public Button getWeeklyButton() {
		return this.viewWeekly;
	}
	
	
	
	private void viewWeekly() {
		// TODO Auto-generated method stub
		Stage weeklyStage = new Stage();
		weeklyStage.setTitle("Weekly View");
		this.view = new WeeklyCalendarView(YearMonth.now()).getView();
//		weeklyStage.setScene(new Scene(new WeeklyCalendarView(YearMonth.now()).getView()));
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
			node.setDate(currDate); // set the date of the node
			
			Text text = new Text(String.valueOf(currDate.getDayOfMonth())); // the number of the day
			
			
//			Text testText = new Text("this is a test"); // testing
			
			
			double anchorVal = 5.0;
			node.setTopAnchor(text, anchorVal); // set the top anchor of the child to be offset by 5
			node.setLeftAnchor(text,  anchorVal); // set the left anchor of the number to be offset by 5
			node.getChildren().add(text); // add the number text to the anchor pane's children
			
			
			
			
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
	 * Getter for all the calendar days
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
