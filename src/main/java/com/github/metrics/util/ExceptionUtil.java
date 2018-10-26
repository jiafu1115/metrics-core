package com.github.metrics.util;

public class ExceptionUtil {
	
	private ExceptionUtil() {
		//no instance
	}
	
    public static Throwable getRootCause(Throwable exception) {
    	if (exception == null) {
    		return null;
    	}
    	Throwable cause = exception.getCause();
    	if(cause == null) {
    		return exception;
    	}
    	 
    	return getRootCause(cause);
    }

}
