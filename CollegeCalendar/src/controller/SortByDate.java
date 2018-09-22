package controller;

import java.util.Comparator;

import gui.EventGO;
import javafx.scene.control.Button;

public class SortByDate implements Comparator<Button>{
	public int compare(Button a, Button b) {
		EventGO ego1 = (EventGO)a.getUserData();
		EventGO ego2 = (EventGO)b.getUserData();
		return ego2.getDate().compareTo(ego1.getDate());
	}
}
