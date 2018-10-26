import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import com.github.metrics.Metric;
import com.github.metrics.elements.Application;
import com.github.metrics.elements.Environment;
import com.github.metrics.elements.event.ApiCallEvent;
import com.github.metrics.elements.event.Event;
import com.github.metrics.elements.event.UsageEvent;
import com.github.metrics.handler.MetricsHandlerImpl;
import com.github.metrics.handler.output.MetricOutputerable;
import com.github.metrics.handler.output.OutputMetricByConsole;
import com.github.metrics.handler.output.OutputMetricByLog;

public class TestMetrics {
	
	
 	public static void output(Metric metrics) {
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
    
 	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Application application = new Application("application", "my component", "1.0");
		Environment environment = new Environment("producation", "10.224.2.116");
		OutputMetricByConsole toConsoleOutputMetric = new OutputMetricByConsole();
		OutputMetricByLog toLogOutputMetric = new OutputMetricByLog();
		Collection<MetricOutputerable> collect = new ArrayList<>();
		collect.add(toConsoleOutputMetric);
		collect.add(toLogOutputMetric);
  		MetricsHandlerImpl metricsHandlerImpl = new MetricsHandlerImpl(application, environment, collect);
 		Event event = new ApiCallEvent.Builder("status", "trackingId_1111").buildFailedApiCall(100, 401, "auth failed");
 		
 		//TestMetrics.output(new Metric(application, environment, event));
 		//metricsHandlerImpl.input(event);
		
		TimeUnit.SECONDS.sleep(100);
 		
	}

}
