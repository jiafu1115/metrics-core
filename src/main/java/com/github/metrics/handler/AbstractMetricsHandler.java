package com.github.metrics.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.github.metrics.Metric;
import com.github.metrics.elements.Application;
import com.github.metrics.elements.Environment;
import com.github.metrics.elements.event.Event;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
 
public abstract class AbstractMetricsHandler implements MetricsHandler {

	private static final Logger LOGGER = Logger.getLogger(AbstractMetricsHandler.class);
 	
	private static final ExecutorService WRITE_METRIC_THREAD = getMetricThread();

	private static ThreadPoolExecutor getMetricThread() {
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("metric-thread-%d").build();
		return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
		        new LinkedBlockingQueue<Runnable>(100_000), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	private Application application;
	private Environment environment;
    
    protected AbstractMetricsHandler(Application application, Environment environment) {
    	this.application = application;
    	this.environment = environment;
    }

	@Override
	public void input(Event event) {
 		WRITE_METRIC_THREAD.submit(() -> {
			
 			Metric metric = convertFromEventToMetrics(event);
			try {
				output(metric);
			} catch (Exception e) {
				LOGGER.error("write metric fail for: " + e.getMessage(), e);
			}

		});
	  
 	}

	private Metric convertFromEventToMetrics(Event event) {
		return new Metric(application, environment, event);
	}

	protected abstract void output(Metric metric);

    public static void shutdown() {
		WRITE_METRIC_THREAD.shutdownNow();
	}
 
}
