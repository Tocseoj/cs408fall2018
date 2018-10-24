package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContactDialog {

	private GUIController guiController;
	
	private Controller c  = new Controller();

	public ContactDialog(Stage primaryStage, EventGO editEvent, GUIController guiController) {
		this.guiController = guiController;
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		// Setup Container
		int width = 300;
		int height = 100;
		ScrollPane scrollWrapper = new ScrollPane();
		scrollWrapper.setPrefSize(width, height);
		VBox containerPane = new VBox();

		TextField title = new TextField();
		containerPane.getChildren().add(new Label("Contact Name"));
		containerPane.getChildren().add(title);

		Button submit = new Button("Add Contact");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				addContact(guiController.getUsername(), title.getText());
			}
		});
		containerPane.getChildren().add(submit);

		Button delete = new Button("Remove Contact");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				deleteContact(guiController.getUsername(), title.getText());
				dialog.close();
			}
		});
		containerPane.getChildren().add(delete);
		
		// Add container to scene to stage
		scrollWrapper.setContent(containerPane);
		Scene dialogScene = new Scene(scrollWrapper, width, height);
		dialog.setScene(dialogScene);

		dialog.show();
	}
	
	private void addContact(String userName, String contactName) {
		ContactGO cgo = new ContactGO("", userName, contactName);
		c.addContactToDatabase(cgo);
		if(!cgo.getID().equals("")) {
			guiController.addToContactList(cgo);
			guiController.addContactEvent(cgo);
		}
	}
	
	private void deleteContact(String userName, String contactName) {
		String sid = guiController.removeFromContactList(userName, contactName);
		c.deleteContactFromDatabase(sid);
		guiController.deleteContactEventFromDbAndLocal("Contact " + contactName, userName);
	}
}
