package com.njb.msscbeerservice.services.inventory;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.njb.msscbeerservice.services.inventory.model.BeerInventoryDto;

@FeignClient(name = "beer-inventory-failover")
public interface InventoryFailOverFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "beer-inventory-failover")
	ResponseEntity<List<BeerInventoryDto>> getOnhandInventory();
}
