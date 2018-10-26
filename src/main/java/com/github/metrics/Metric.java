package com.github.metrics;

import com.github.metrics.elements.Application;
import com.github.metrics.elements.Environment;
import com.github.metrics.elements.event.Event;

import lombok.Data;

@Data
public class Metric {
	
    private Application application;
    private Environment environment;
    private Event event;
    
	public Metric(Application application, Environment environment, Event event) {
		this.application = application;
		this.environment = environment;
		this.event = event;
	}
 
}
