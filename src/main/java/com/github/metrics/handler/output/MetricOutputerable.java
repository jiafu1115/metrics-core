package com.github.metrics.handler.output;


import com.github.metrics.Metric;

public interface MetricOutputerable {
	
 	public boolean isNeedOutput(Metric metrics);

 	public void output(Metric metrics);

}
