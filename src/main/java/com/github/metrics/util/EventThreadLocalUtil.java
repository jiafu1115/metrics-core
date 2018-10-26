package com.github.metrics.util;

import com.github.metrics.elements.event.Event;

public class EventThreadLocalUtil {
	
    private static final ThreadLocal<Event> EVENT_THREAD_LOCAL = new ThreadLocal<Event>();
  
    public static Event getBoundEvent(){
        return EVENT_THREAD_LOCAL.get();
    }
  
    public static void boundEvent(Event event){
    	EVENT_THREAD_LOCAL.set(event);
    } 

	public static void setBoundEvent(String attributeName, Object attributeValue){
		Event event = getBoundEvent();
    	if(event != null) {
    		event.appendInformation(attributeName, attributeValue);
    	}
     }
	
	private EventThreadLocalUtil() {
		//no instance
	}
    
}
