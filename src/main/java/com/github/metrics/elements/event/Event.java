package com.github.metrics.elements.event;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public abstract class Event{
	
	private final String type;
    private final String name;
    private final String trackingID;
    private final long timestamp;
    
    private Map<String, Object> others = new LinkedHashMap<String, Object>(10);

	protected Event(String type, String name, String trackingID) {
		this.type = type;
		this.name = name;
		this.trackingID = trackingID;
		this.timestamp = System.currentTimeMillis();
	}
	
	public void appendInformation(String key, Object value) {
		this.others.put(key, value);
	}
  
}