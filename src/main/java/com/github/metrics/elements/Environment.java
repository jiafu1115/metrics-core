package com.github.metrics.elements;

import lombok.Data;

@Data
public class Environment {
	
    private String address;
    private String environment;
    
	public Environment(String environment, String address) {
		this.address = address;
		this.environment = environment;
	}

}