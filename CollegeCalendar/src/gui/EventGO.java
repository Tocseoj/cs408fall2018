package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.DayOfWeek;

enum EventType { GENERIC, HOMEWORK, CLASS, EXAM, MEETING };

public class EventGO {
	private EventType type;
	private String id;
	private String title;
	private LocalDate date;
	private LocalTime time;
	private Duration duration;
	private int priority;
	private Boolean[] repeatDays = new Boolean[8];
	private LocalDate endRepeat;
	private Duration notificationOffset;
	private Boolean completed;
	
	public EventGO(String id, String title) {
		this.id = id;
		this.title = title;
		this.date = LocalDate.now();
		this.time = LocalTime.of(0, 0);
		this.duration = Duration.ofHours(0);
		this.priority = 0;
		this.repeatDays[this.date.getDayOfWeek().getValue()] = true;
		this.endRepeat = this.date;
		this.notificationOffset = Duration.ofMinutes(10);
		this.completed = false;
	}
	
	public EventGO(String id, String title, LocalDate date) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = LocalTime.of(0, 0);
		this.duration = Duration.ofHours(0);
		this.priority = 0;
		this.repeatDays[this.date.getDayOfWeek().getValue()] = true;
		this.endRepeat = this.date;
		this.notificationOffset = Duration.ofMinutes(10);
		this.completed = false;
	}
	
	public EventGO(String id, String title, LocalDate date, LocalTime time, Duration duration) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.priority = 0;
		this.repeatDays[this.date.getDayOfWeek().getValue()] = true;
		this.endRepeat = this.date;
		this.notificationOffset = Duration.ofMinutes(10);
		this.completed = false;
	}
	
	public EventGO(String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, LocalDate endRepeat, Duration notificationOffset) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.priority = priority;
		this.repeatDays = repeatDays;
		this.endRepeat = endRepeat;
		this.notificationOffset = notificationOffset;
		this.completed = false;
	}
	
	public String getID() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean val) {
		completed = val;
	}
}
