package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsernameDialog {

	public UsernameDialog(Stage primaryStage, GUIController guiController) {
				
		VBox container = new VBox();
		Label inputLabel = new Label("Enter your username.");
		TextField inputField = new TextField();
		// DEBUG
		inputField.setText("tester");
		Button submitButton = new Button("Submit");
		
		//
		// Event Handlers
		//
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				
				String username = null;
				
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
					
					// update Username
					guiController.setUsername(username);
				}
			}
		});
		
		
		//
		// Setup Scene
		//
		
		container.getChildren().addAll(inputLabel, inputField, submitButton);
		
		
		StackPane loginRoot = new StackPane();
		loginRoot.getChildren().add(container);
		
		Scene scene = new Scene(loginRoot, 200, 100);
		
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		
		dialog.setScene(scene);
		dialog.setTitle("Username Form");
		dialog.show();
		
	}
	
}
