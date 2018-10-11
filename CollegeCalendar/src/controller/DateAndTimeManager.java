package controller;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateAndTimeManager {
	
	static private LocalDate currentDate;
	static private LocalTime currentTime;
	
	private LocalDate viewingDate;
	private LocalTime viewingTime;
	
	public DateAndTimeManager() {
		viewingDate = getCurrentDate();
		viewingTime = getCurrentTime();
	}
	
	public static LocalDate getCurrentDate() {
		currentDate = LocalDate.now();
		return currentDate;
	}
	
	public static LocalTime getCurrentTime() {
		currentTime = LocalTime.now();
		return currentTime;
	}
	
	public void setViewingDate(LocalDate date) {
		viewingDate = date;
	}
	
	public void setViewingTime(LocalTime time) {
		viewingTime = time;
	}
	
	public LocalDate getViewingDate() {
		return viewingDate;
	}
	
	public LocalTime getViewingTime() {
		return viewingTime;
	}

}
