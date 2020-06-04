package com.njb.msscbeerservice.services.inventory;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.njb.msscbeerservice.services.inventory.model.BeerInventoryDto;

@FeignClient(name = "beer-inventory-service")
public interface InventoryServiceFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = BeerInventoryServiceImplRestTemplate.INVENTORY_PATH)
	ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(@PathVariable UUID beerId);
}
