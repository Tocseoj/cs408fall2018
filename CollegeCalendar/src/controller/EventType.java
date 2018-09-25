package controller;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
	GENERIC(1), HOMEWORK(2), CLAS(3), EXAM(4), MEETING(4);
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
