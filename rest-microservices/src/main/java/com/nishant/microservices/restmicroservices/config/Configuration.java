package com.nishant.microservices.restmicroservices.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties("limits-service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

	private int minimum;
	private int maximum;

}
