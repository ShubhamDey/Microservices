package com.in28minutes.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.in28minutes.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration config;
	
	@GetMapping(path = "/hardcoded-limits")
	public LimitConfiguration retriveHardcodedLimits() {
		return new LimitConfiguration(1000, 1);
	}
	
	@GetMapping(path = "/application.properties-limits")
	public LimitConfiguration retriveLimitsFromApplicationProperties() {
		return new LimitConfiguration(config.getMaximum(), config.getMinimum());
	}
	
	@GetMapping(path = "/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetriveConfiguration")
	public LimitConfiguration retriveConfiguration() {
		throw new RuntimeException("Not available");
	}
	
	public LimitConfiguration fallbackRetriveConfiguration() {
		return new LimitConfiguration(999, 9);
	}
	
}
