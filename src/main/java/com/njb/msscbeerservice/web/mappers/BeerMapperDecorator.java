package com.njb.msscbeerservice.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import com.njb.msscbeerservice.domain.Beer;
import com.njb.msscbeerservice.services.inventory.BeerInventoryService;
import com.njb.msscbeerservice.web.model.BeerDto;

public class BeerMapperDecorator implements BeerMapper {

	@Autowired
	private BeerInventoryService beerInvenotryService;

	@Autowired
	private BeerMapper mapper;

	@Override
	public BeerDto beerToBeerDto(Beer beer) {
		BeerDto dto = mapper.beerToBeerDto(beer);
		dto.setQuantityOnHand(beerInvenotryService.getOnhandInventory(beer.getId()));
		return dto;
	}

	@Override
	public Beer beerDtoToBeer(BeerDto dto) {
		return mapper.beerDtoToBeer(dto);
	}

}
