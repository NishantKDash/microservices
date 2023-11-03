package com.nishant.microservices.currencyexchange.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nishant.microservices.currencyexchange.models.CurrencyExchange;
import com.nishant.microservices.currencyexchange.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	
	@Autowired
	private Environment environment;
     
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private CurrencyExchangeRepository repository;
   
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getExchange(@PathVariable String from, @PathVariable String to)
    {
    	//CurrencyExchange exchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(20));
    	logger.info("RetriveExchangeValue called from {" + from + "} to {" + to + "}");
    	
    	//2023-11-03T21:31:19.034+05:30  INFO [currency-exchange,77870a5c2a7395b07ee11e8bf74985d8,23d0f3e72cac3a66] 11888 --- [currency-exchange] [nio-8000-exec-1] [77870a5c2a7395b07ee11e8bf74985d8-23d0f3e72cac3a66] c.n.m.c.c.CurrencyExchangeController     : RetriveExchangeValue called from {USD} to {INR}
    	//the ids in the above line is given by the micrometer to trace the req
    	CurrencyExchange exchange = repository.findByFromAndTo(from, to);
    	if(exchange == null)
    		throw new RuntimeException("Unable to find Data for " + from + " to " + to);
    	exchange.setEnvironment(environment.getProperty("local.server.port"));
    	return exchange;
    }
}
