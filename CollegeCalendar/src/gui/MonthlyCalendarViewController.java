package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.YearMonth;

public class MonthlyCalendarViewController extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MyCalendarApp");
        primaryStage.setScene(new Scene(new MonthlyCalendarView(YearMonth.now()).getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
