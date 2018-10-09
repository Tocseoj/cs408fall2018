package gui;

import controller.Controller;
import controller.DateAndTimeManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIController {
	
	private Stage primaryStage;
	private double sceneWidth;
	private double sceneHeight;
	private Paint backgroundColor;
	
	private CalendarViews calendarView;
	private DateAndTimeManager date;
	private String username;
	private Pane dynamicPane;
	
	private Controller controller;
	
	public GUIController(Stage primaryStage,  double sceneWidth, double sceneHeight, Paint backgroundColor) {
		this.primaryStage = primaryStage;
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		this.backgroundColor = backgroundColor;
		
		controller = new Controller();
		
		calendarView = new MonthlyGUI(controller);
		date = new DateAndTimeManager();
		username = "tester";
		
		dynamicPane = calendarView.getCalendarView(date.getViewingDate(), username);
	}
	
	public void changePrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void showUsernamePopup() {
		
		VBox container = new VBox();
		Label inputLabel = new Label("Enter your username.");
		TextField inputField = new TextField();
		// DEBUG
		inputField.setText("tester");
		Button submitButton = new Button("Submit");
		
		//
		// Event Handlers
		//
		submitButton.setOnAction(this::handleUsernamePopupSubmit);
		
		
		//
		// Setup Scene
		//
		
		container.getChildren().addAll(inputLabel, inputField, submitButton);
		
		
		StackPane loginRoot = new StackPane();
		loginRoot.getChildren().add(container);
		
		Scene scene = new Scene(loginRoot, 200, 100, backgroundColor);
		
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		
		dialog.setScene(scene);
		dialog.setTitle("Username Form");
		dialog.show();
	}
	
	private void handleUsernamePopupSubmit(ActionEvent event) {
		Button submit = (Button)event.getSource();
		Pane dialogContainer = (Pane)submit.getParent();
		for (Node node : dialogContainer.getChildrenUnmodifiable()) {
			if (node instanceof TextField) {
				username = ((TextField)node).getText();
				if (username.equals("")) {
					username = null;
				}
				break;
			}
		}
		Stage dialog = (Stage)dialogContainer.getScene().getWindow();	
		if (username != null) {
			dialog.close();
			
			updateDynamicPane();
		}
	}
	
	public Pane getDynamicPane() {
		return dynamicPane;
	}
	
	private void updateDynamicPane() {
		Pane parent = (Pane)dynamicPane.getParent();
		parent.getChildren().remove(dynamicPane);
		dynamicPane = calendarView.getCalendarView(date.getViewingDate(), username);
		HBox.setHgrow(dynamicPane, Priority.ALWAYS);
		parent.getChildren().add(0, dynamicPane);
	}
	
	public void switchView(String viewName) {
		if (viewName.equals("Calendar View") || viewName.equals("Monthly View")) {
			if (calendarView instanceof MonthlyGUI) {
				return;
			}
			calendarView = new MonthlyGUI(controller);
			updateDynamicPane();
		}
		else if (viewName.equals("Weekly View")) {
			if (calendarView instanceof WeeklyGUI) {
				return;
			}
			calendarView = new WeeklyGUI(controller);
			updateDynamicPane();
		}
	}
	
	public void leftArrow() {
		if (calendarView instanceof MonthlyGUI) {
			date.setViewingDate(date.getViewingDate().minusMonths(1));
		}
		else if (calendarView instanceof WeeklyGUI) {
			date.setViewingDate(date.getViewingDate().minusWeeks(1));
		}
		updateDynamicPane();
	}
	public void rightArrow() {
		if (calendarView instanceof MonthlyGUI) {
			date.setViewingDate(date.getViewingDate().plusMonths(1));
		}
		else if (calendarView instanceof WeeklyGUI) {
			date.setViewingDate(date.getViewingDate().plusWeeks(1));
		}
		updateDynamicPane();
	}
	public void todayButton() {
		date.setViewingDate(DateAndTimeManager.getCurrentDate());
		updateDynamicPane();
	}
	public void addEventButton() {
		EventDialog ed = new EventDialog(primaryStage, null, username, controller, this);
	}
	
	public void addEventToView(EventGO event) {
		calendarView.removeFromView(event);
	}
	
	public void removeEventFromView(EventGO event) {
		
	}
	
}
