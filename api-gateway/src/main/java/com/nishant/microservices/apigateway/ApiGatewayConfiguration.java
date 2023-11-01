package com.nishant.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder)
	{
		return builder.routes()
				.route(p->
					p.path("/get")
					.filters(f -> f.addRequestHeader("MyHeader", "MyURI").addRequestHeader("Bearer", "4jhj4h4j").addRequestParameter("ID","9"))
					.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**")
						      .uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion-rest/**")
						      .uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
						      .uri("lb://currency-conversion"))
				.route(p -> p.path("/kingKohli/**")
						      .filters(f -> f.rewritePath("/kingKohli/(?<segment>.*)", "/currency-conversion-feign/${segment}"))
						      .uri("lb://currency-conversion"))
				.build();
		
		// in the second route , we just create simplified route removing the name of application ,ex - localhost:8765/currency-exchange/**
		// any req to this route gets forwarded to the proper route defined in our microservices using eureka name server and also loadbalanced (lb)
		// for this we have to disable discovery locator and lowercaseserviceid as well
		//spring.cloud.gateway.discovery.locator.enabled=true
        //spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true
		
		
		// in the fourth route  we created a custom route, but this just gets forwareded in a loadbalanced way to the microservice route using eureka
		//works without the segment though
	}
}
