package guitests;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import gui.*;


public class DelunTemp extends Application {
	public void init() throws Exception{}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene( Sandbox.weekView(), 800, 500);
			primaryStage.setTitle("Hi everyone!");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
