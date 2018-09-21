package gui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class AnchorPaneNode extends AnchorPane{
	private LocalDate date; // this is the date that is associated with this particular day in the calendar
	
	/*
	 * 
	 */
	public AnchorPaneNode(Node... children) {
		super(children);
		
		this.setOnMouseClicked(e -> System.out.println("Today's date is + " + this.date));
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
}
