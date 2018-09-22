package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.YearMonth;

public class CalendarTester extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("This is a calendar tester");
        primaryStage.setScene(new Scene(new MonthlyCalendarView(YearMonth.now()).getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
