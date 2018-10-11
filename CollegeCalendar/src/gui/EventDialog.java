package gui;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

import controller.Controller;
import controller.EventType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventDialog {
	
	private GUIController guiController;

	public EventDialog(Stage primaryStage, EventGO editEvent, GUIController guiController) {

		this.guiController = guiController;
		
		// Setup Dialog
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		// Setup Container
		int width = 400;
		int height = 600;
		ScrollPane scrollWrapper = new ScrollPane();
		scrollWrapper.setPrefSize(width, height);
		VBox containerPane = new VBox();
		//        containerPane.setPrefSize(width, height);

		//        VBox column1 = new VBox();
		//        VBox column2 = new VBox();
		//        containerPane.getChildren().add(column1);
		//        containerPane.getChildren().add(column2);

		// Add components
		//        TextField eventName = new TextField();
		//        containerPane.getChildren().add(eventName);
		final ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(getNames(EventType.class));
		comboBox.setValue("GENERIC");

		Label completedL = new Label("Homework Completed?");
		CheckBox completed = new CheckBox();

		Label dateL = new Label("Event Date");

		comboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				ComboBox<String> self = (ComboBox<String>)e.getSource();
				if (self.getValue().equals("HOMEWORK")) {
					int index = containerPane.getChildren().indexOf(self);
					containerPane.getChildren().add(index + 1, completed);
					containerPane.getChildren().add(index + 1, completedL);
					dateL.setText("Due Date");
				} else {
					containerPane.getChildren().remove(completedL);
					containerPane.getChildren().remove(completed);
					dateL.setText("Event Date");
				}
			}
		});

		containerPane.getChildren().add(new Label("Event Type"));
		containerPane.getChildren().add(comboBox);
		TextField title = new TextField();
		containerPane.getChildren().add(new Label("Event Name"));
		containerPane.getChildren().add(title);
		containerPane.getChildren().add(dateL);
		DatePicker datePicker = new DatePicker();
		containerPane.getChildren().add(datePicker);
		containerPane.getChildren().add(new Label("Event Time"));
		TextField time = new TextField();
		containerPane.getChildren().add(time);
		containerPane.getChildren().add(new Label("Duration (min)"));
		TextField duration = new TextField();
		containerPane.getChildren().add(duration);

		containerPane.getChildren().add(new Label("Priority"));
		TextField priority = new TextField();
		containerPane.getChildren().add(priority);

		Label endRepeatL = new Label("End Repeat");
		DatePicker endRepeat = new DatePicker();
		Label repeatDaysL = new Label("Repeat on Days");
		HBox repeatDays = new HBox();
		for (int i = 0; i < 7; i++) {
			VBox vb = new VBox();
			vb.getChildren().add(new CheckBox());
			Label l = new Label();
			DayOfWeek day = DayOfWeek.of(((i == 0) ? 7 : i));
			l.setText(day.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.US));
			vb.getChildren().add(l);
			repeatDays.getChildren().add(vb);
		}

		CheckBox repeat = new CheckBox();
		repeat.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				CheckBox self = (CheckBox)e.getSource();
				if (self.isSelected()) {
					int index = containerPane.getChildren().indexOf(self);
					containerPane.getChildren().add(index + 1, repeatDays);
					containerPane.getChildren().add(index + 1, repeatDaysL); 
					containerPane.getChildren().add(index + 1, endRepeat);
					containerPane.getChildren().add(index + 1, endRepeatL); 
				} else {
					containerPane.getChildren().remove(endRepeatL);
					containerPane.getChildren().remove(endRepeat);
					containerPane.getChildren().remove(repeatDays);
					containerPane.getChildren().remove(repeatDaysL); 
				}
			}
		});
		
		

		//        containerPane.getChildren().add(endRepeatL);
		//        containerPane.getChildren().add(endRepeat);

		Label notificationOffsetL = new Label("Minutes before to notify");
		TextField notificationOffset = new TextField();

		containerPane.getChildren().add(new Label("Notify Me"));
		CheckBox notify = new CheckBox();
		notify.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				CheckBox self = (CheckBox)e.getSource();
				if (self.isSelected()) {
					int index = containerPane.getChildren().indexOf(self);
					containerPane.getChildren().add(index + 1, notificationOffset);
					containerPane.getChildren().add(index + 1, notificationOffsetL);
				} else {
					containerPane.getChildren().remove(notificationOffsetL);
					containerPane.getChildren().remove(notificationOffset);
				}
			}
		});
		containerPane.getChildren().add(notify);
		containerPane.getChildren().add(new Label("Repeat"));
		containerPane.getChildren().add(repeat);
		
		CheckBox NotifyWhenEventOver = new CheckBox();
		containerPane.getChildren().add(new Label("NotifyWhenEventOver"));
		containerPane.getChildren().add(NotifyWhenEventOver);
		
		CheckBox ConstantReminderDuringEvent = new CheckBox();
		containerPane.getChildren().add(new Label("ConstantReminderDuringEvent"));
		containerPane.getChildren().add(ConstantReminderDuringEvent);
		
		Button submit = new Button("Add Event");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				Boolean is_completed = false;
				if (comboBox.getValue().equals("HOMEWORK")) {
					is_completed = completed.isSelected();
				}
				String no = "0";
				if (notify.isSelected()) {
					no = notificationOffset.getText();
				}
				Boolean[] rpt = { false, false, false, false, false, false, false };
				LocalDate edrpt = datePicker.getValue();
				if (repeat.isSelected()) {
					edrpt = endRepeat.getValue();
					for (int i = 0; i < repeatDays.getChildren().size(); i++) {
						VBox vb = (VBox)repeatDays.getChildren().get(i);
						CheckBox c = (CheckBox)vb.getChildren().get(0);
						rpt[i] = c.isSelected();
						//	    				System.out.println((i) + ": " + c.isSelected());
					}
				}
				
				//Gus added notification Vars
				Boolean allottedTimeUp = NotifyWhenEventOver.isSelected();
				Boolean constantReminder = ConstantReminderDuringEvent.isSelected();

				String test_id = "";
				if (editEvent != null) {
					test_id = editEvent.getID();
					
//					guiController.removeEventFromView(editEvent);
					
//					System.out.println(test_id);
//					//							events.remove(editEvent); TODO
//					controller.updateEventInDatabase(editEvent);
//					//							removeEvent(editEvent);
				}

				EventGO eventToBeAdded = addEvent(EventType.valueOf(comboBox.getValue()), test_id, title.getText(), datePicker.getValue(), time.getText(), duration.getText(), priority.getText(), rpt, edrpt, no, is_completed, allottedTimeUp, constantReminder);
//				if(!eventToBeAdded.getID().equals("")) {
					
//				guiController.addEventToView(eventToBeAdded);
					//							events.add(eventToBeAdded); TODO
//				}

				//						redrawCalendarView(); TODO

				dialog.close();
			}
		});
		containerPane.getChildren().add(submit);

		if (editEvent != null) {
			comboBox.setValue(editEvent.getType().toString());
			title.setText(editEvent.getTitle());
			datePicker.setValue(editEvent.getDate());
			time.setText(editEvent.getTime().toString());
			duration.setText(Long.toString(editEvent.getDuration().toMinutes()));
			priority.setText(editEvent.getPriority() + "");
			notify.setSelected(!(editEvent.getNotificationOffset().isZero()));
			if (notify.isSelected()) {
				CheckBox self = notify;
				if (self.isSelected()) {
					int index = containerPane.getChildren().indexOf(self);
					containerPane.getChildren().add(index + 1, notificationOffset);
					containerPane.getChildren().add(index + 1, notificationOffsetL);
				} else {
					containerPane.getChildren().remove(notificationOffsetL);
					containerPane.getChildren().remove(notificationOffset);
				}
				notificationOffset.setText(editEvent.getNotificationOffset().toMinutes() + "");
			}
			Boolean is_checked = false;
			for (int i = 0; i < editEvent.getRepeatDays().length; i++) {
				if (editEvent.getRepeatDays()[i]) {
					System.out.println("TRUE");
					is_checked = true;
					break;
				}
			}

			repeat.setSelected((( !editEvent.getDate().isEqual(editEvent.getEndRepeat()) )) || is_checked);
			if (repeat.isSelected()) {
				CheckBox self = repeat;
				if (self.isSelected()) {
					int index = containerPane.getChildren().indexOf(self);
					containerPane.getChildren().add(index + 1, repeatDays);
					containerPane.getChildren().add(index + 1, repeatDaysL); 
					containerPane.getChildren().add(index + 1, endRepeat);
					containerPane.getChildren().add(index + 1, endRepeatL); 
				} else {
					containerPane.getChildren().remove(endRepeatL);
					containerPane.getChildren().remove(endRepeat);
					containerPane.getChildren().remove(repeatDays);
					containerPane.getChildren().remove(repeatDaysL); 
				}
				endRepeat.setValue(editEvent.getEndRepeat());
				for (int i = 0; i < repeatDays.getChildren().size(); i++) {
					VBox vb = (VBox)repeatDays.getChildren().get(i);
					CheckBox c = (CheckBox)vb.getChildren().get(0);
					c.setSelected(editEvent.getRepeatDays()[i]);
				}
			}
			if (editEvent.getType() == EventType.HOMEWORK) {
				ComboBox<String> self = comboBox;
				if (self.getValue().equals("HOMEWORK")) {
					int index = containerPane.getChildren().indexOf(self);
					containerPane.getChildren().add(index + 1, completed);
					containerPane.getChildren().add(index + 1, completedL);
					dateL.setText("Due Date");
				} else {
					containerPane.getChildren().remove(completedL);
					containerPane.getChildren().remove(completed);
					dateL.setText("Event Date");
				}
			}

			dialog.setTitle("Editing Event " + editEvent.getTitle());

			submit.setText("Confirm Changes");
			Button delete = new Button("Delete Event");
			delete.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					
					if (editEvent != null) {
//						guiController.removeEventFromView(editEvent);
//						System.out.println("BALEETED!");
						guiController.deleteEvent(editEvent);
					}
					//							events.remove(editEvent);
					//							removeEvent(editEvent);
					//							redrawCalendarView();
					dialog.close();
					
				}
			});
			containerPane.getChildren().add(delete);
		} else {
			dialog.setTitle("Adding New Event");
		}

		// Add container to scene to stage
		scrollWrapper.setContent(containerPane);
		Scene dialogScene = new Scene(scrollWrapper, width, height);
		dialog.setScene(dialogScene);

		dialog.show();
	}

	private static String[] getNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}

	public EventGO addEvent(EventType type,
			String id,
			String title,
			LocalDate date,
			String time,
			String duration,
			String priority,
			Boolean[] repeatDays,
			LocalDate endRepeat,						// If endRepeat == date then no repeat
			String notificationOffset,			// If negative, then notifications off
			Boolean completed, 
			Boolean allottedTimeUp,
			Boolean constantReminder) {		

		LocalTime ptime = LocalTime.now();
		if (!time.equals("")) {
			try {
				ptime = LocalTime.parse(time);
			}
			catch (DateTimeParseException e) {
				System.out.println("Time Invalid");
			}
		}
		Duration pduration = Duration.ofMinutes(0);
		if (!duration.equals("")) {
			try {
				pduration = Duration.ofMinutes(Long.parseLong(duration));
			}
			catch (Exception e) {
				System.out.println("Duration Invalid");
			}
		}
		int ppriority = 0;
		if (!priority.equals("")) {
			try {
				ppriority = Integer.parseInt(priority);
			} catch (Exception e) {
				System.err.println("Priority invalid");
			}
		}
		Duration poffset = Duration.ofMinutes(0);
		if (notificationOffset != "") {
			try {
				poffset = Duration.ofMinutes(Long.parseLong(notificationOffset));
			} catch (Exception e) {
				System.err.println("Offset invalid");
			}
		}

		if (date == null) {
			date = LocalDate.now();
			endRepeat = date;
		}
		if (title.equals("")) {
			title = "(No Title)";
		}

		EventGO e = new EventGO(type, id, title, date, ptime, pduration, ppriority, repeatDays, endRepeat, poffset, completed, guiController.getUsername(), allottedTimeUp, constantReminder);

		if (id != "") {
//			controller.updateEventInDatabase(e);
			guiController.updateEvent(e);
		} else {
			guiController.addEvent(e);
		}
		//		events.add(e);
		//		System.out.print("[");
		//		for (int i = 0; i < 7; i++) {
		//			if (i + 1 < 7) {
		//				System.out.print(e.getRepeatDays()[i] + ", ");
		//			} else {
		//				System.out.print(e.getRepeatDays()[i] + "]\n");
		//			}
		//		}

		return e;
	}

}
