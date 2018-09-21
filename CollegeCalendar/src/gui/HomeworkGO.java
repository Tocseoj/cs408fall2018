package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class HomeworkGO extends GenericGO {
	private Boolean completed;
	
	public HomeworkGO(String id, String title) {
		super(id, title);
		completed = false;
	}
	public HomeworkGO(String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, LocalDate endRepeat, Duration notificationOffset) {
		super(id, title, date, time, duration, priority, endRepeat, notificationOffset);
		completed = false;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean val) {
		completed = val;
	}
	
	
}
