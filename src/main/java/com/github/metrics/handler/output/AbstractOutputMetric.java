package com.github.metrics.handler.output;

import com.github.metrics.Metric;

public abstract class AbstractOutputMetric implements MetricOutputerable {
	
	@Override
	public boolean isNeedOutput(Metric metrics) {
 		return true;
	}
	
}
