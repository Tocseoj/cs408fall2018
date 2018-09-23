package gui;

import java.time.YearMonth;

import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.YearMonth;

 

public class WeeklyCalendarViewController extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MyCalendarApp");
        primaryStage.setScene(new Scene(new WeeklyCalendarView(YearMonth.now()).getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
