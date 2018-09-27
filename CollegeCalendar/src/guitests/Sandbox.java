package guitests;
import gui.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sandbox {
	static final int MINDAYWIDTH = 80;
	public static GridPane makeWeekdays()
	{
		GridPane week = new GridPane();
		StackPane daysOfWeek[] = new StackPane[7];
		int counter;
		
		for (counter = 0; counter < 7; ++counter) {
			daysOfWeek[counter] = new StackPane();
			daysOfWeek[counter].setMinWidth(MINDAYWIDTH);
			daysOfWeek[counter].setPrefHeight(100);
			daysOfWeek[counter].setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

			Rectangle temp = new Rectangle(50, 50, Color.CADETBLUE);
			CalEventButton pressable = new CalEventButton("buttontext");
			StackPane.setAlignment(temp, Pos.TOP_CENTER);
			StackPane.setAlignment(pressable, Pos.BOTTOM_CENTER);
			
			
			daysOfWeek[counter].getChildren().add(temp);
			daysOfWeek[counter].getChildren().add(pressable);
			
			week.addColumn(counter, daysOfWeek[counter]);
			
			ColumnConstraints constrain = new ColumnConstraints();
			constrain.setPercentWidth(100.0 / 7.0);
			week.getColumnConstraints().add(constrain);
		}
		
		week.setAlignment(Pos.TOP_CENTER);
		week.setHgap(10);
		
		return week;
	}
	
	public static BorderPane weekView() {
		BorderPane root = new BorderPane();
		root.setMinWidth(500);
		root.setMinHeight(400);

		Rectangle rect = new Rectangle();
		rect.setX(0.0); 
		rect.setY(0.0); 
		rect.setWidth(150.0); 
		rect.setHeight(150.0d); 
		
		rect.setArcWidth(30.0);
		rect.setArcHeight(30.0);
		
		Rectangle topBorder = new Rectangle(400.0, 50.0, Color.BLUEVIOLET);
		topBorder.setArcWidth(20.0);
		topBorder.setArcHeight(20.0);
		BorderPane.setAlignment(topBorder, Pos.TOP_CENTER);
		
		GridPane weekGrid = makeWeekdays();
		root.setCenter(weekGrid);
		root.setTop(topBorder);

		
		return root;
	}
}
