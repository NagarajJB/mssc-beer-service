package com.njb.msscbeerservice.services;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.njb.model.BeerDto;
import com.njb.model.BeerPagedList;
import com.njb.model.BeerStyleEnum;

public interface BeerService {
	BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

	BeerDto saveNewBeer(BeerDto beerDto);

	BeerDto updateBeer(UUID beerId, BeerDto beerDto);

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventoryOnHand);

	BeerDto getByUpc(String upc);
}
