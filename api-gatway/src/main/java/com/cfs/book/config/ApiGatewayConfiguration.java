package com.cfs.book.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("Ola", "Claudio").addRequestParameter("Ola", "Claudio"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/product-service/**").uri("lb://product-service"))
				.route(p -> p.path("/category-service/**").uri("lb://product-service"))
				.route(p -> p.path("/movement-service/**").uri("lb://movement-service"))
				.route(p -> p.path("/stock-service/**").uri("lb://stock-service"))
				
				.build();
	}
}
