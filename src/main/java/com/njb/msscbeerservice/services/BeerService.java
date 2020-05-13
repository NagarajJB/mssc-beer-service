package com.njb.msscbeerservice.services;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.njb.msscbeerservice.web.model.BeerDto;
import com.njb.msscbeerservice.web.model.BeerPagedList;
import com.njb.msscbeerservice.web.model.BeerStyleEnum;

public interface BeerService {
	BeerDto getById(UUID beerId);

	BeerDto saveNewBeer(BeerDto beerDto);

	BeerDto updateBeer(UUID beerId, BeerDto beerDto);

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of);
}
