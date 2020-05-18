package com.njb.msscbeerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JmsConfig {

	public final static String BREWING_REQUEST_QUEUE = "brewing-request";
	public final static String NEW_INVENTORY_QUEUE = "new-inventory";

	@Bean
	public MessageConverter messageConverter(ObjectMapper objectMapper) {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		// configure message converter to use spring boot managed object mapper
		converter.setObjectMapper(objectMapper);
		return converter;
	}
}
