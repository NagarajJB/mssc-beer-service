package com.njb.msscbeerservice.services;

import java.util.List;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.njb.msscbeerservice.config.JmsConfig;
import com.njb.msscbeerservice.domain.Beer;
import com.njb.msscbeerservice.events.BrewBeerEvent;
import com.njb.msscbeerservice.repositories.BeerRepository;
import com.njb.msscbeerservice.services.inventory.BeerInventoryService;
import com.njb.msscbeerservice.web.mappers.BeerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService {

	private final BeerRepository beerRepository;
	private final BeerInventoryService beerInventoryService;
	private final JmsTemplate jmsTemplate;
	private final BeerMapper beerMapper;

	@Scheduled(fixedRate = 5000)
	public void checkForLowInventory() {
		List<Beer> beers = beerRepository.findAll();

		beers.forEach(beer -> {
			Integer onhandInventory = beerInventoryService.getOnhandInventory(beer.getId());
			log.debug("qty on hand" + beer.getMinOnHand());
			log.debug("onhandInventory " + onhandInventory);

			if (beer.getMinOnHand() >= onhandInventory) {
				jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
						new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
			}
		});

	}

}
