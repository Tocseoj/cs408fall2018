package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class HomeworkGO extends EventGO {
	private Boolean completed;
	
	public HomeworkGO(String id, String title) {
		super(id, title);
		completed = false;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean val) {
		completed = val;
	}
	
	
}
