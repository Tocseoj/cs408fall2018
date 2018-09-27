package controller;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
	GENERIC(0), HOMEWORK(1), CLASS(2), EXAM(3), MEETING(4);
	private int value;
    private static Map map = new HashMap<>();
    
    private EventType(int value) {
        this.value = value;
    }
    
    static {
        for (EventType eventType : EventType.values()) {
            map.put(eventType.value, eventType);
        }
    }
 	public static EventType valueOf(int eventType) {
        return (EventType) map.get(eventType);
    }
	
	public int getValue() {
        return value;
    }
}
