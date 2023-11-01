package com.nishant.microservices.currencyconversion.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nishant.microservices.currencyconversion.models.CurrencyConversion;


@FeignClient(name = "currency-exchange" , url = "localhost:8000")
public interface CurrencyExchangeProxy {
	
	  @GetMapping("/currency-exchange/from/{from}/to/{to}")
	    public CurrencyConversion getExchange(@PathVariable String from, @PathVariable String to);

}
