package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping(path = "/hardcoded-currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveHardcodedValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue value = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65));
		value.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return value;
	}
	
	@GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue value = repository.findByFromAndTo(from, to);
		value.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("{}", value);
		return value;
	}
	
}
