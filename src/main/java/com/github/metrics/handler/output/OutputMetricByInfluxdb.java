package com.github.metrics.handler.output;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import com.github.metrics.Metric;
import com.github.metrics.elements.event.ApiCallEvent;
import com.github.metrics.elements.event.Event;
import com.github.metrics.elements.event.UsageEvent;

public class OutputMetricByInfluxdb extends AbstractOutputMetric {
	
	private InfluxDB influxDB;
 	
	public OutputMetricByInfluxdb(String influxdbUrl, String userName, String password, String database) {
 		this.influxDB = InfluxDBFactory.connect(influxdbUrl, userName, password);
		this.influxDB.setDatabase(database);
		this.influxDB.disableBatch();
	}

	@Override
	public void output(Metric metrics) {
 		Builder measurement = Point.measurement(metrics.getApplication().getService());
  		
 		measurement.time(metrics.getEvent().getTimestamp(), TimeUnit.MILLISECONDS);
 		
 		measurement.tag("component", metrics.getApplication().getComponent());
 		measurement.tag("version", metrics.getApplication().getVersion());
 		
 		measurement.tag("environment", metrics.getEnvironment().getEnvironment());
 		measurement.tag("address", metrics.getEnvironment().getAddress());
  		
 		Event event = metrics.getEvent();
 		measurement.tag("type", event.getType());
 		measurement.tag("name", event.getName());
 		
 		measurement.addField("trackingId", event.getTrackingID());
 		
 		Map<String, Object> others = event.getOthers();
 		Set<Entry<String, Object>> entrySet = others.entrySet();
 		for (Entry<String, Object> entry : entrySet) {
 			measurement.addField(entry.getKey(), String.valueOf(entry.getValue()));
		}
 		
 		if(event instanceof ApiCallEvent) {
 			ApiCallEvent apiCallEvent = (ApiCallEvent)event;
 			measurement.addField("success", apiCallEvent.isSuccess());
 			measurement.addField("responseCode", apiCallEvent.getResponseCode());
 			measurement.addField("totalDurationInMS", apiCallEvent.getTotalDurationInMS());
 			measurement.addField("failReason", apiCallEvent.getFailReason());
  		}else if (event instanceof UsageEvent){
  			UsageEvent usageEvent = (UsageEvent)event;
  			Map<String, AtomicLong> statistics = usageEvent.getStatistics();
  			for (Entry<String, AtomicLong> entry : statistics.entrySet()) {
  	 			measurement.addField(entry.getKey(), entry.getValue().toString());
			}
  		}
 		
 		Point point = measurement.build();
		influxDB.write(point);
   	}
 

}
