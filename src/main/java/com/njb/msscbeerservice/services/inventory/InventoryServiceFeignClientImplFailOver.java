package com.njb.msscbeerservice.services.inventory;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.njb.msscbeerservice.services.inventory.model.BeerInventoryDto;

import lombok.RequiredArgsConstructor;
/*
 * Implements the feign client of happy path, InventoryServiceFeignClient
 */

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientImplFailOver implements InventoryServiceFeignClient {
	
	private final InventoryFailOverFeignClient inventoryFailOverFeignClient;
	
	@Override
	public ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(UUID beerId) {
		return inventoryFailOverFeignClient.getOnhandInventory();
	}

}
