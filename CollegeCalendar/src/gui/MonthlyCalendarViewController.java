package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.YearMonth;

public class MonthlyCalendarViewController extends Application {
	
	Stage stage; // The main stage
	
	Button viewWeekly; // MonthlyView button that leads to weekly view
	Button userOptions; // MonthlyView button that renders user options
	
	Button weekToMonthButton; // the button on the weekly view that leads to monthly view
	
	Scene monthlyScene; // the monthly view scene
	Scene weeklyScene; // the weekly view scene
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		
        stage.setTitle("MyCalendarApp");
        
        MonthlyCalendarView monthlyView = new MonthlyCalendarView(YearMonth.now());
        
        
        this.viewWeekly = monthlyView.getWeeklyButton();
        this.userOptions = monthlyView.getUserOptionsButton();
        
        this.userOptions.setOnAction(e -> showUserOptions());
        
        this.viewWeekly.setOnAction(e -> showWeeklyScene());
        
        
        
        VBox monthVBox = monthlyView.getView();
        
        Scene monthlyScene = new Scene(monthlyView.getView());
        this.monthlyScene = monthlyScene;
        
        WeeklyCalendarView weeklyView = new WeeklyCalendarView(YearMonth.now());
        
        Scene weeklyScene = new Scene(weeklyView.getView());
        this.weeklyScene = weeklyScene;
        
        this.weekToMonthButton = weeklyView.getMonthlyViewButton();
        this.weekToMonthButton.setOnAction(e -> showMonthlyScene());
        
        stage.setScene(monthlyScene);
        stage.show();
    }
	
	
	private void showMonthlyScene() {
		// TODO Auto-generated method stub
		stage.setScene(monthlyScene);
	}


	/*
	 * Set the scene to the weekly scene
	 */
	private void showWeeklyScene() {
		// TODO Auto-generated method stub
		stage.setScene(weeklyScene);
	}


	/*
	 * Change the scene to the user options scene
	 * 
	 * TODO actually make a user options scene
	 */
    private void showUserOptions() {
		// TODO Auto-generated method stub
		
	}
    

	public static void main(String[] args) {
        launch(args);
    }
}
