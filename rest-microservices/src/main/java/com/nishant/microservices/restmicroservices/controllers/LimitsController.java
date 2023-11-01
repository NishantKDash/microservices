package com.nishant.microservices.restmicroservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishant.microservices.restmicroservices.config.Configuration;
import com.nishant.microservices.restmicroservices.models.Limits;

@RestController
@RequestMapping("")
public class LimitsController {

	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public Limits retriverLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}

}
