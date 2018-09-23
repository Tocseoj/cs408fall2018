package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.YearMonth;

public class MonthlyCalendarViewController extends Application {
	
	Stage stage;
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
        primaryStage.setTitle("MyCalendarApp");
        primaryStage.setScene(new Scene(new MonthlyCalendarView(YearMonth.now()).getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // Getter for this stage
    public Stage getStage() {
    	return this.stage;
    }
    
    // Setter for the stage of this controller
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
}
