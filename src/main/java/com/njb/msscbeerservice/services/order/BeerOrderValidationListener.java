package com.njb.msscbeerservice.services.order;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.njb.model.events.ValidateOrderRequest;
import com.njb.model.events.ValidateOrderResult;
import com.njb.msscbeerservice.config.JmsConfig;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

	private final JmsTemplate jmsTemplate;
	private final BeerOrderValidator beerOrderValidator;

	@JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
	public void listen(ValidateOrderRequest validateOrderRequest) {
		Boolean isValid = beerOrderValidator.validateOrder(validateOrderRequest.getBeerOrder());
		jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER__RESPONSE_QUEUE, ValidateOrderResult.builder()
				.isValid(isValid).orderId(validateOrderRequest.getBeerOrder().getId()).build());
	}

}
