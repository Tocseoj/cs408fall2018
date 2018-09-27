package gui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import controller.EventType;

public class EventGO {
	private EventType type;
	private String id;
	private String title;
	private LocalDate date;
	private LocalTime time;
	private Duration duration;
	private int priority;
	private Boolean[] repeatDays = new Boolean[8];
	private LocalDate endRepeat;						// If endRepeat == date then no repeat
	private Duration notificationOffset;				// If negative, then notifications off
	private Boolean completed;
	private String userName;
	
	public EventGO(EventType type, String id, String title, String userName) {
		this.setType(type);
		this.id = id;
		this.title = title;
		this.date = LocalDate.now();
		this.time = LocalTime.of(0, 0);
		this.duration = Duration.ofHours(0);
		this.setPriority(0);
		this.repeatDays[this.date.getDayOfWeek().getValue()] = true;
		this.setEndRepeat(this.date);
		this.setNotificationOffset(Duration.ofMinutes(10));
		this.completed = false;
		this.userName = userName;
	}
	
	public EventGO(EventType type, String id, String title, LocalDate date, String userName) {
		this.setType(type);
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = LocalTime.of(0, 0);
		this.duration = Duration.ofHours(0);
		this.setPriority(0);
		this.repeatDays[this.date.getDayOfWeek().getValue()] = true;
		this.setEndRepeat(this.date);
		this.setNotificationOffset(Duration.ofMinutes(10));
		this.completed = false;
		this.userName = userName;

	}
	
	public EventGO(String id, String title, LocalDate date, LocalTime time, Duration duration, String userName) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.setPriority(0);
		this.repeatDays[this.date.getDayOfWeek().getValue()] = true;
		this.setEndRepeat(this.date);
		this.setNotificationOffset(Duration.ofMinutes(10));
		this.completed = false;
		this.userName = userName;

	}
	
	public EventGO(EventType type, String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, Boolean[] repeatDays, LocalDate endRepeat, Duration notificationOffset, String userName) {
		this.setType(type);
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.setPriority(priority);
		this.repeatDays = repeatDays;
		this.setEndRepeat(endRepeat);
		this.setNotificationOffset(notificationOffset);
		this.completed = false;
		this.userName = userName;

	}
	
	public EventGO(EventType type, String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, Boolean[] repeatDays, LocalDate endRepeat, Duration notificationOffset, boolean completed,String userName) {
		this.setType(type);
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.setPriority(priority);
		this.repeatDays = repeatDays;
		this.setEndRepeat(endRepeat);
		this.setNotificationOffset(notificationOffset);
		this.completed = completed;
		this.userName = userName;

	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Duration getNotificationOffset() {
		return notificationOffset;
	}

	public void setNotificationOffset(Duration notificationOffset) {
		this.notificationOffset = notificationOffset;
	}

	public LocalDate getEndRepeat() {
		return endRepeat;
	}

	public void setEndRepeat(LocalDate endRepeat) {
		this.endRepeat = endRepeat;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	public Boolean[] getRepeatDays() {
		return repeatDays;
	}

	public void setRepeatDays(Boolean[] repeatDays) {
		this.repeatDays = repeatDays;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
