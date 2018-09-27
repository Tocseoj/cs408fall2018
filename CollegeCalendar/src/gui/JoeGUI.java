package gui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Locale;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JoeGUI extends Application {
	
	LocalDate date;
	LocalDate monthBeingViewed;
	
	static ArrayList<EventGO> events;
	
	// Change these 4 lines to edit grid and add/subtract rows or columns
	ColumnConstraints[] columnList = new ColumnConstraints[7];
	RowConstraints[] rowList = new RowConstraints[8];
	int[] calendarColumns = {0, 6};
	int[] calendarRows = {2, 7};
	
	Stage primaryStage;
	GridPane gridpane = new GridPane();
//	Button[][] buttonChildren = new Button[6][7];
	Label monthYear = new Label();
	DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
	
		
	public static void main(String[] args) {
		events = getAllEvents();
//		System.out.println(events);
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		//
		// Setup Calendar Grid
		//
//		GridPane gridpane = new GridPane();
		gridpane.getStyleClass().add("grid-pane");
		
		// Change these 4 lines to edit grid and add/subtract rows or columns
//		ColumnConstraints[] columnList = new ColumnConstraints[7];
//		RowConstraints[] rowList = new RowConstraints[8];
//		int[] calendarColumns = {0, 6};
//		int[] calendarRows = {2, 7};
		//
		
		for (int c = 0; c < columnList.length; c++) {
			columnList[c] = new ColumnConstraints();
			if (c >= calendarColumns[0] && c <= calendarColumns[1]) {	
				columnList[c].setPercentWidth(100);
			}
			columnList[c].setHgrow(Priority.ALWAYS) ; // allow column to grow
			columnList[c].setFillWidth(true); // ask nodes to fill space for column
		}
		gridpane.getColumnConstraints().addAll(columnList);
		for (int r = 0; r < rowList.length; r++) {
			rowList[r] = new RowConstraints();
			if (r >= calendarRows[0] && r <= calendarRows[1]) {
				rowList[r].setPercentHeight(100);
			}
			rowList[r].setVgrow(Priority.ALWAYS) ; // allow row to grow
			rowList[r].setFillHeight(true); // ask nodes to fill height for row
		}
		gridpane.getRowConstraints().addAll(rowList);
		
		//
		// Setup calendar components
		//
		this.date = LocalDate.now();
		this.monthBeingViewed = this.date;
//	    DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		
		// Month Label and month change buttons
//		Label monthYear = new Label();
		monthYear.getStyleClass().add("day-of-week-label");
		monthYear.setText(monthBeingViewed.format(monthYearFormatter));
		gridpane.add(monthYear, 3, 0, 3, 1);
		Button left = new Button("<");
		left.getStyleClass().add("month-change-button");
		left.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	monthBeingViewed = monthBeingViewed.minusMonths(1);
		    	redrawCalendarView();
		    }
		});
		gridpane.add(left, 1, 0);
		Button right = new Button(">");
		right.getStyleClass().add("month-change-button");
		right.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	monthBeingViewed = monthBeingViewed.plusMonths(1);
		    	redrawCalendarView();
		    }
		});
		gridpane.add(right, 2, 0);
		Button today = new Button("Today");
		today.getStyleClass().add("month-change-button");
		today.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	date = LocalDate.now();
		    	monthBeingViewed = date;
		    	redrawCalendarView();
		    }
		});
		gridpane.add(today, 0, 0);
		Button view = new Button("Calendar View");
		view.getStyleClass().add("month-change-button");
		view.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	System.out.println("Change View");
		    }
		});
		gridpane.add(view, 6, 0);
//		Button addEvent = new Button("Add Event");
//		addEvent.getStyleClass().add("month-change-button");
//		addEvent.setOnAction(new EventHandler<ActionEvent>() {
//		    @Override public void handle(ActionEvent e) {
//		    	addEventDialog();
//		    }
//		});
//		gridpane.add(addEvent, 6, 7);
		// Day of week Labels
	    for (int c = 0; c < 7; c++) {
	    	Label l = new Label();
	    	l.getStyleClass().add("day-of-week-label");
	    	DayOfWeek day = DayOfWeek.of(((c == 0) ? 7 : c));
	    	l.setText(day.getDisplayName(TextStyle.FULL_STANDALONE, Locale.US));	
	    	gridpane.add(l, c, 1);
	    }
	    
	    // Calendar Day Buttons
	    redrawCalendarView();
		
	    //
	    // Setup Scene
	    //
	    StackPane root = new StackPane();
	    
        root.getChildren().add(gridpane);

        Scene scene = new Scene(root, 1024, 640);
        scene.getStylesheets().add("gui/joe-gui.css");

        primaryStage.setTitle("College Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void redrawCalendarView() {
		LocalDate firstOfMonth = monthBeingViewed.with(TemporalAdjusters.firstDayOfMonth());
	    LocalDate lastOfMonth = monthBeingViewed.with(TemporalAdjusters.lastDayOfMonth());
	    
	    EventHandler<ActionEvent> calendarDayEvent = new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String dateClicked = ((Button)e.getSource()).getText();
		    	viewDay(dateClicked);
		    }
		};
		
		ArrayList<Node> toRemove = new ArrayList<>();
		for (Node node : gridpane.getChildren()) {
			int r = GridPane.getRowIndex(node);
			int c = GridPane.getColumnIndex(node);
			if (calendarRows[0] <= r && r <= calendarRows[1]) {
				if (calendarColumns[0] <= c && c <= calendarColumns[1]) {
					toRemove.add(node);
				}
			}
		}
		for (Node node : toRemove) {
			gridpane.getChildren().remove(node);
		}
		
		for (int r = calendarRows[0]; r <= calendarRows[1]; r++) {
	    	for (int c = calendarColumns[0]; c <= calendarColumns[1]; c++) {
//	    		if (buttonChildren[r - calendarRows[0]][c - calendarColumns[0]] != null) {
//	    			gridpane.getChildren().remove(buttonChildren[r - calendarRows[0]][c - calendarColumns[0]]);
//	    			buttonChildren[r - calendarRows[0]][c - calendarColumns[0]] = null;
//	    		}
	    		int dayOfMonth = ((r - calendarRows[0]) * 7) + ((c - calendarColumns[0]) + 1) - (firstOfMonth.getDayOfWeek().getValue() == 7 ? 0 : firstOfMonth.getDayOfWeek().getValue());
	    		if (dayOfMonth >= 1 && dayOfMonth <= lastOfMonth.getDayOfMonth()) {
		    		Button b = new Button(String.valueOf(dayOfMonth));
		    		b.getStyleClass().add("calendar-day-button");
		    		if (monthBeingViewed.getMonth() == date.getMonth() && dayOfMonth == date.getDayOfMonth()) {
		    			b.getStyleClass().add("today");
		    		}
		    		
		    		b.setOnAction(calendarDayEvent);
		    		gridpane.add(b, c, r);
		    		
		    		// Add events
		    		ArrayList<EventGO> daysEvents = getEventOnDay(firstOfMonth.plusDays(dayOfMonth - 1));
		    		for (int i = 0; i < daysEvents.size(); i++) {
		    			EventGO e = daysEvents.get(i);
		    			Button event = new Button(e.getTitle());
		    			event.getStyleClass().add("calendar-day-events");
		    			gridpane.add(event, c, r);
		    		}
		    		
//		    		buttonChildren[r - calendarRows[0]][c - calendarColumns[0]] = b;
	    		}
	    	}
	    }
		
		monthYear.setText(monthBeingViewed.format(monthYearFormatter));
		
		Button addEvent = new Button("Add Event");
		addEvent.getStyleClass().add("month-change-button");
		addEvent.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	addEventDialog();
		    }
		});
		gridpane.add(addEvent, 6, 7);
	}
	
	private ArrayList<EventGO> getEventOnDay(LocalDate day) {
		ArrayList<EventGO> e = new ArrayList<>();;
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getDate().equals(day)) {
				e.add(events.get(i));
			}
		}
		return e;
	}
	
	private void viewDay(String day) {
		// TODO Auto-generated method stub
		System.out.println("Viewing day " + day);
		
		int width = 300;
		int height = 200;
		
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(width, height);
        
        VBox dialogVbox = new VBox();
        for (int i = 0; i < 30; i++) {
        	dialogVbox.getChildren().add(new Text("This is a Dialog"));
        }
        scroll.setContent(dialogVbox);

        
        Scene dialogScene = new Scene(scroll, width, height);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	
	private void addEventDialog() {
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
        
        containerPane.getChildren().add(new Label("Event Name"));
        containerPane.getChildren().add(new TextField());
        containerPane.getChildren().add(new Label("Event Date"));
        containerPane.getChildren().add(new DatePicker());
        containerPane.getChildren().add(new Label("Event Time"));
        containerPane.getChildren().add(new TextField());
        containerPane.getChildren().add(new Label("Duration"));
        containerPane.getChildren().add(new TextField());
        
        containerPane.getChildren().add(new Label("Priority"));
        containerPane.getChildren().add(new TextField());
        
        Label endRepeatL = new Label("End Repeat");
        TextField endRepeat = new TextField();
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
        
        Label notificationOffsetL = new Label("(Hours:)Minutes before to notify");
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
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	System.out.println("Hello");
		    	EventGO event = new EventGO(""+0, "New Event", LocalDate.now(), "joe");
		    	events.add(event);
		    	redrawCalendarView();
		    }
		});
        containerPane.getChildren().add(submit);
        
        // Add container to scene to stage
        scrollWrapper.setContent(containerPane);
        Scene dialogScene = new Scene(scrollWrapper, width, height);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	
	private static ArrayList<EventGO> getAllEvents() {
		// TODO Get real events
		ArrayList<EventGO> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			EventGO e = new EventGO(""+i, "Test Event", LocalDate.now().plusDays(i + 1), "joe");
			list.add(e);
		}
		return list;
	}
}
