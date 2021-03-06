package com.njb.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.njb.msscbeerservice.services.inventory.model.BeerInventoryDto;

import lombok.extern.slf4j.Slf4j;

@Profile("!local-discovery & !digitalocean")
@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = true)
@Service
public class BeerInventoryServiceImplRestTemplate implements BeerInventoryService {

	public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

	private final RestTemplate restTemplate;

	/*
	 * @Value used while constructing bean
	 * ignoreUnknownFields = true
	 * @ConfigurationProperties for beerInventoryServiceHost, as its used while
	 * using restTemplate RestTemplateBuilder into constructor, the values will be
	 * null if we use @ConfigurationProperties in constructor use @Value to
	 * externalize props as we need it during construction time of bean
	 */

	public BeerInventoryServiceImplRestTemplate(RestTemplateBuilder restTemplateBuilder,
			@Value("${sfg.brewery.inventory-user}") String inventoryUser,
			@Value("${sfg.brewery.inventory-password}") String inventoryPassword) {
		this.restTemplate = restTemplateBuilder.basicAuthentication(inventoryUser, inventoryPassword).build();
	}

	private String beerInventoryServiceHost;

	public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
		this.beerInventoryServiceHost = beerInventoryServiceHost;
	}

	@Override
	public Integer getOnhandInventory(UUID beerId) {
		log.debug("calling inventory service");

		ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(
				beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BeerInventoryDto>>() {
				}, (Object) beerId);
		Integer onHand = Objects
				.requireNonNull(responseEntity.getBody().stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum());

		return onHand;
	}

}
