package com.njb.msscbeerservice.services.order;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.njb.model.events.BeerOrderDto;
import com.njb.msscbeerservice.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeerOrderValidator {
	private final BeerRepository beerRepository;

	public Boolean validateOrder(BeerOrderDto beerOrder) {

		AtomicInteger beersNotFound = new AtomicInteger();
		beerOrder.getBeerOrderLines().forEach(orderLine -> {

			if (beerRepository.findByUpc(orderLine.getUpc()) == null) {

				beersNotFound.incrementAndGet();
			}

		});
		return beersNotFound.get() == 0;
	}
}
