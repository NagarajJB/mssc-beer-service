package com.njb.msscbeerservice.services.brewing;

import java.util.List;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.njb.common.events.BrewBeerEvent;
import com.njb.msscbeerservice.config.JmsConfig;
import com.njb.msscbeerservice.domain.Beer;
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

	@Scheduled(fixedRate = 5000) // every 5 seconds
	public void checkForLowInventory() {
		List<Beer> beers = beerRepository.findAll();

		beers.forEach(beer -> {
			Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());
			log.debug("Checking Inventory for: " + beer.getBeerName() + " / " + beer.getId());
			log.debug("Min Onhand is: " + beer.getMinOnHand());
			log.debug("Inventory is: " + invQOH);

			if (beer.getMinOnHand() >= invQOH) {
				jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
						new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
			}
		});

	}

}
