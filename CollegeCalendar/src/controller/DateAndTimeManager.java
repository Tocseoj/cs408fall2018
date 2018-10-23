package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateAndTimeManager {
	
	static private LocalDate currentDate;
	static private LocalTime currentTime;
	
	static private LocalDateTime currentDateTime;
	
	private LocalDate viewingDate;
	private LocalTime viewingTime;
	
	private LocalDateTime viewingDateTime;
	
	public DateAndTimeManager() {
		viewingDate = getCurrentDate();
		viewingTime = getCurrentTime();
		
		viewingDateTime = getCurrentDateTime();
	}
	
	public static LocalDate getCurrentDate() {
		currentDate = LocalDate.now();
		return currentDate;
	}
	
	public static LocalTime getCurrentTime() {
		currentTime = LocalTime.now();
		return currentTime;
	}
	
	public static LocalDateTime getCurrentDateTime() {
		currentDateTime = LocalDateTime.now();
		return currentDateTime;
	}
	
	public void setViewingDate(LocalDate date) {
		viewingDate = date;
	}
	
	public void setViewingTime(LocalTime time) {
		viewingTime = time;
	}
	
	public void setViewingDateTime(LocalDateTime time) {
		viewingDateTime = time;
	}
	
	public LocalDate getViewingDate() {
		return viewingDate;
//		return currentDate;
	}
	
	public LocalTime getViewingTime() {
		return viewingTime;
	}

	public LocalDateTime getViewingDateTime() {
		return viewingDateTime;
	}
}
