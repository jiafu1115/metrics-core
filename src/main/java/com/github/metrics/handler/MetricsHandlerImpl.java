package com.github.metrics.handler;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.github.metrics.Metric;
import com.github.metrics.elements.Application;
import com.github.metrics.elements.Environment;
import com.github.metrics.handler.output.MetricOutputerable;

public class MetricsHandlerImpl extends AbstractMetricsHandler {

	private static final Logger LOGGER = Logger.getLogger(MetricsHandlerImpl.class);

	private final Collection<MetricOutputerable> metricOutputerables;

	public MetricsHandlerImpl(Application application, Environment environment, Collection<MetricOutputerable> metricOutputerables) {
		super(application, environment);
		this.metricOutputerables = metricOutputerables;
 	}
 
 
	@Override
	protected void output(Metric metrics) {
		for (MetricOutputerable metricOutputerable : metricOutputerables) {
			try {
 				if (metricOutputerable.isNeedOutput(metrics)) {
 					metricOutputerable.output(metrics);
 				}
			} catch (Exception e) {
				LOGGER.error("write metric fail for: " + e.getMessage(), e);
			}
		}
	}

}
