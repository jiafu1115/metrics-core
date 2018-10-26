package com.github.metrics.elements.event;

import lombok.Data;

@Data
public class ApiCallEvent extends Event {

	private boolean success;
	private long responseCode;
	private long totalDurationInMS;
	private String failReason;

	private ApiCallEvent(String name, String trackingId) {
		super("interface", name, trackingId);
	}

	public static class Builder {

		private ApiCallEvent apiCallEvent;

		public Builder(String name, String trackingId) {
			this.apiCallEvent = new ApiCallEvent(name, trackingId);
		}

		public Builder appendInformation(String key, long value) {
			this.apiCallEvent.appendInformation(key, value);
			return this;
		}

		public ApiCallEvent buildSuccessApiCall(long totalDurationInMS, long responseCode) {
			this.apiCallEvent.setSuccess(true);
			this.apiCallEvent.setResponseCode(responseCode);
			this.apiCallEvent.setTotalDurationInMS(totalDurationInMS);
			return apiCallEvent;
		}

		public ApiCallEvent buildFailedApiCall(long totalDurationInMS, long responseCode, String failReason) {
			this.apiCallEvent.setSuccess(false);
			this.apiCallEvent.setResponseCode(responseCode);
			this.apiCallEvent.setTotalDurationInMS(totalDurationInMS);
			this.apiCallEvent.setFailReason(failReason);
			return apiCallEvent;
		}

	}

}
