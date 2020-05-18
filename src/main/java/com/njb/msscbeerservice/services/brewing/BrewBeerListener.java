package com.njb.msscbeerservice.services.brewing;

import javax.transaction.Transactional;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.njb.msscbeerservice.config.JmsConfig;
import com.njb.msscbeerservice.domain.Beer;
import com.njb.msscbeerservice.events.BrewBeerEvent;
import com.njb.msscbeerservice.events.NewInventoryEvent;
import com.njb.msscbeerservice.repositories.BeerRepository;
import com.njb.msscbeerservice.web.model.BeerDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {
	private final BeerRepository beerRepository;
	private final JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
	public void listenToBrewBeerRequest(BrewBeerEvent brewBeerEvent) {
		BeerDto beerDto = brewBeerEvent.getBeerDto();
		// because qty to brew isnt exposed
		Beer beer = beerRepository.getOne(beerDto.getId());

		// could be complex logic, to illustrate just set
		// accessing outside transactional, method should be @transactional

		beerDto.setQuantityOnHand(beer.getQuantityToBrew());

		log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

		NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

		jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
	}
}
