# metrics-core

  ```
	public static void main(String[] args) throws InterruptedException {
		
		Application application = new Application("my application", "my component", "1.0");
		Environment environment = new Environment("producation", "10.224.2.116");
 		Collection<MetricOutputerable> metricOutputerables = newOutputs();
 		
  	MetricsHandlerImpl metricsHandlerImpl = new MetricsHandlerImpl(application, environment, metricOutputerables);
  		
 		Event event = new ApiCallEvent.Builder("createUser", "trackingId").buildFailedApiCall(200, 401, "auth failed");
 		metricsHandlerImpl.input(event);
   		
	}
  ```


  ```
	private static Collection<MetricOutputerable> newOutputs() {
		OutputMetricByConsole toConsoleOutputMetric = new OutputMetricByConsole();
		OutputMetricByLog toLogOutputMetric = new OutputMetricByLog();
		OutputMetricByInfluxdb outputMetricByInfluxdb = new OutputMetricByInfluxdb("http://10.224.2.147:8086", "admin", "admin", "DSA");
		
		Collection<MetricOutputerable> metricOutputerables = new ArrayList<>();
		metricOutputerables.add(toConsoleOutputMetric);
		metricOutputerables.add(toLogOutputMetric);
		metricOutputerables.add(outputMetricByInfluxdb);
    
		return metricOutputerables;
	}
  ```
