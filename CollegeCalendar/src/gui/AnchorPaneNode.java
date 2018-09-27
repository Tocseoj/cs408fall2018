package gui;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *  Extended version of the JavaFx AnchorPane type.
 *  Adds functionality of storing dates. 
 **/

public class AnchorPaneNode extends AnchorPane {
	private LocalDate date; // this is the date that is associated with this particular day in the calendar
	
	private ArrayList<EventGO> events;
	/*
	 * 
	 */
	public AnchorPaneNode(Node... children) {
		super(children);
		
//		Controller controller = new Controller();
		
//		this.events = controller.getAllEvents("tester");
		
		EventGO event = new EventGO("1", "testing");
		this.events.add(event);
		
		this.setOnMouseClicked(e -> System.out.println("You clicked on  " + event.getTitle()));
	}
	
	
	/*
	 * Getter for the date
	 */
	public LocalDate getDate() {
		return this.date;
	}
	
	
	/*
	 * Setter for the date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	/*
	 * Getter for the events of this node
	 */
	public ArrayList<EventGO> getEvents() {
		return this.events;
	}
	
	/*
	 * 
	 */
	public void setEvents(ArrayList<EventGO> events) {
		this.events = events;
	}
	
	/*
	 * Helper function to print the contents of this day's events
	 */
	public void printEvents() {
		for (EventGO event : this.events) {
			System.out.println(event.getTitle());
		}
	}
}
