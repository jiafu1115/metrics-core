package com.github.metrics.handler.output;


import com.github.metrics.Metric;
import com.github.metrics.util.JsonUtil;

 
public class OutputMetricByConsole extends AbstractOutputMetric {
   

	@Override
	public void output(Metric metrics) {
		System.out.println(JsonUtil.toJson(metrics));
 	}
  
}
