package com.nishant.microservices.currencyconversion.Controllers;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nishant.microservices.currencyconversion.models.CurrencyConversion;
import com.nishant.microservices.currencyconversion.proxies.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion-rest/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionViaRest(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class, uriVariables);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setEnvironment("From:"+ env.getProperty("local.server.port") + " " + "ExchangeData:" + currencyConversion.getEnvironment());
		currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
		return currencyConversion;
	}
	
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionViaFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		CurrencyConversion currencyConversion = proxy.getExchange(from, to);
		currencyConversion.setQuantity(quantity);
		currencyConversion.setEnvironment("From:" + env.getProperty("local.server.port") + " " + "ExchangeData:" + currencyConversion.getEnvironment());
		currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
		return currencyConversion;
	}

}
