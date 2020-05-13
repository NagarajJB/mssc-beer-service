package com.njb.msscbeerservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.njb.msscbeerservice.bootstrap.BeerLoader;
import com.njb.msscbeerservice.services.inventory.BeerInventoryService;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

	@Autowired
	BeerInventoryService service;

	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void testGetOnhandInventory() {
		System.out.println("test..");
		Integer qoh = service.getOnhandInventory(BeerLoader.BEER_3_UUID);
		System.out.println(qoh);
	}

}
