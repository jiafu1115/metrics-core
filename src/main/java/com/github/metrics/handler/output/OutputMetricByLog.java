package com.github.metrics.handler.output;


import org.apache.log4j.Logger;

import com.github.metrics.Metric;
import com.github.metrics.util.JsonUtil;

 
public class OutputMetricByLog extends AbstractOutputMetric {

	private static final Logger LOGGER = Logger.getLogger(OutputMetricByLog.class);
  
	@Override
	public void output(Metric metrics) {
		LOGGER.info(JsonUtil.toJson(metrics));
 	}
  
}
