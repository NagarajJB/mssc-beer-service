package com.njb.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.njb.msscbeerservice.services.inventory.model.BeerInventoryDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BeerInventoryServiceImplFeign implements BeerInventoryService {

	private final InventoryServiceFeignClient inventoryServiceFeignClient;

	@Override
	public Integer getOnhandInventory(UUID beerId) {
		log.debug("Calling inventory service beerId :" + beerId);

		ResponseEntity<List<BeerInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnhandInventory(beerId);

		Integer onHand = Objects.requireNonNull(responseEntity.getBody()).stream()
				.mapToInt(BeerInventoryDto::getQuantityOnHand).sum();

		log.debug("BeerId: " + beerId + " onHand is :" + onHand);

		return onHand;

	}

}
