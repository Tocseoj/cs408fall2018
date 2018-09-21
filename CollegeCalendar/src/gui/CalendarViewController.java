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
import javafx.stage.Stage;
//import javafx.*;


public class CalendarViewController extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	@FXML
	private ChoiceBox monthChoiceBox;

//	
//	ObservableList<String> monthChoiceList = FXCollections.observableArrayList("January", "February", "March", "April", "May",
//			"June", "July", "August", "September", "October", "November", "December");
	
	@FXML
	private Button monthChoiceSubmitButton;
	
	
	@FXML
	public Button viewUserOptionsButton;
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Parent root = FXMLLoader.load(getClass().getResource("MainCalendarView.fxml"));
		
		
		Scene scene = new Scene(root, 700, 500);
		
		primaryStage.setTitle("MyCalendarApp");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML
	private void handleUserOptionsButton() {
		
	}
	
	
	@FXML
	public void initializeMonthChoiceBox() {
		monthChoiceBox.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November", "December"));
	}
	
	
	/*
	 * Returns the primary stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
