package com.nishant.microservices.currencyexchange.controllers;

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
	
	@Autowired
	private CurrencyExchangeRepository repository;
   
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getExchange(@PathVariable String from, @PathVariable String to)
    {
    	//CurrencyExchange exchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(20));
    	
    	CurrencyExchange exchange = repository.findByFromAndTo(from, to);
    	if(exchange == null)
    		throw new RuntimeException("Unable to find Data for " + from + " to " + to);
    	exchange.setEnvironment(environment.getProperty("local.server.port"));
    	return exchange;
    }
}
