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
	private Boolean allottedTimeUp;
	private Boolean constantReminder;
	
	// some class specific fields
	private String profName;
	private String subjectName;
	private String meetingPersonName;
	

	public EventGO(String title, String userName) {
		this.id = "";
		this.setType(EventType.GENERIC);
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
		this.setConstantReminder(false);
		this.setAllottedTimeUp(false);
		this.profName = "";
		this.subjectName = "";
		this.meetingPersonName = "";
	}

	public EventGO(String id, String title, String userName) {
		this.id = id;
		this.setType(EventType.GENERIC);
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
		this.setConstantReminder(false);
		this.setAllottedTimeUp(false);
		this.profName = "";
		this.subjectName = "";
		this.meetingPersonName = "";
	}
	
	public EventGO(String title, String userName, Duration duration, LocalDate date, LocalTime time) {
		this.id = "";
		this.setType(EventType.GENERIC);
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.setPriority(0);
		this.repeatDays = new Boolean[7];
		for(int i = 0; i < 7; i++) {
			this.repeatDays[i] = false;
		}
		this.setEndRepeat(this.date);
		this.setNotificationOffset(Duration.ofMinutes(10));
		this.completed = false;
		this.userName = userName;
		this.setConstantReminder(false);
		this.setAllottedTimeUp(false);
		this.profName = "";
		this.subjectName = "";
		this.meetingPersonName = "";
	}
	
	public EventGO(EventType type, String id, String title, LocalDate date, LocalTime time, Duration duration, int priority, Boolean[] repeatDays, LocalDate endRepeat, Duration notificationOffset, boolean completed,String userName, Boolean allottedTimeUp, Boolean constantReminder) {
		this.setType(type);
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.priority = priority;
		this.repeatDays = repeatDays;
		this.setEndRepeat(endRepeat);
		this.setNotificationOffset(notificationOffset);
		this.completed = completed;
		this.userName = userName;
		this.setConstantReminder(constantReminder);
		this.setAllottedTimeUp(allottedTimeUp);
		this.profName = "";
		this.subjectName = "";
		this.meetingPersonName = "";
	}
	
	/*
	 * Constructor for the different Event Presets
	 */
	public EventGO(EventType type, String id, String title, LocalDate date, LocalTime time, Duration duration, 
			int priority, Boolean[] repeatDays, LocalDate endRepeat, Duration notificationOffset, boolean completed,
			String userName, Boolean allottedTimeUp, Boolean constantReminder, String profName, String subjectName,
			String meetingPersonName) {
		this.setType(type);
		this.id = id;
		this.title = title;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.priority = priority;
		this.repeatDays = repeatDays;
		this.setEndRepeat(endRepeat);
		this.setNotificationOffset(notificationOffset);
		this.completed = completed;
		this.userName = userName;
		this.setConstantReminder(constantReminder);
		this.setAllottedTimeUp(allottedTimeUp);
		this.profName = profName;
		this.subjectName = subjectName;
		this.meetingPersonName = meetingPersonName;
	}
	
	
	public String getID() {
		if (id == null) {
			return "";	
		}
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		if (title == null) {
			return "";	
		}
		return title;
	}
	
	public LocalDate getDate() {
		if (date == null) {
			return LocalDate.now();	
		}
		return date;
	}
	
	public LocalTime getTime() {
		if (time == null) {
			return LocalTime.now();	
		}
		return time;
	}
	
	public Duration getDuration() {
		if (duration == null) {
			return Duration.ZERO;	
		}
		return duration;
	}
	
	public Boolean getCompleted() {
		if (completed == null) {
			return false;	
		}
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
		if (notificationOffset == null) {
			return Duration.ZERO;	
		}
		return notificationOffset;
	}

	public void setNotificationOffset(Duration notificationOffset) {
		this.notificationOffset = notificationOffset;
	}

	public LocalDate getEndRepeat() {
		if (endRepeat == null) {
			return LocalDate.now();	
		}
		return endRepeat;
	}

	public void setEndRepeat(LocalDate endRepeat) {
		this.endRepeat = endRepeat;
	}

	public EventType getType() {
		if (type == null) {
			return EventType.GENERIC;	
		}
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	public Boolean[] getRepeatDays() {
		if (repeatDays == null) {
			return new Boolean[7];	
		}
		return repeatDays;
	}

	public void setRepeatDays(Boolean[] repeatDays) {
		this.repeatDays = repeatDays;
	}

	public String getUserName() {
		if (userName == null) {
			return "";	
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getAllottedTimeUp() {
		if (allottedTimeUp == null) {
			return false;	
		}
		return allottedTimeUp;
		
	}

	public void setAllottedTimeUp(Boolean allottedTimeUp) {
		this.allottedTimeUp = allottedTimeUp;
	}

	public Boolean getConstantReminder() {
		if (constantReminder == null) {
			return false;	
		}
		return constantReminder;
	}

	public void setConstantReminder(Boolean constantReminder) {
		this.constantReminder = constantReminder;
	}

	/*
	 * Getters and setters for the person with whom the user is meeting with.
	 * Only specified in the Meeting preset.
	 */
	public String getMeetingPersonName() {
		if (meetingPersonName == null) {
			return "";	
		}
		return meetingPersonName;
	}

	public void setMeetingPersonName(String meetingPersonName) {
		this.meetingPersonName = meetingPersonName;
	}

	/*
	 * Getters and setters for the subject name.
	 * Only specified in the Exam preset.
	 */
	public String getSubjectName() {
		if (subjectName == null) {
			return "";	
		}
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/*
	 * Getters and setters for the professor name.
	 * Only specified in the class preset
	 */
	public String getProfName() {
		if (profName == null) {
			return "";	
		}
		return profName;
	}

	public void setProfName(String profName) {
		this.profName = profName;
	}
	
}
