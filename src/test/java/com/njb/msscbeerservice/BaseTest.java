package com.njb.msscbeerservice;

import java.math.BigDecimal;

import com.njb.msscbeerservice.web.model.BeerDto;
import com.njb.msscbeerservice.web.model.BeerStyleEnum;

public class BaseTest {
	BeerDto getValidBeerDto() {
		return BeerDto.builder().beerName("My Beer").beerStyle(BeerStyleEnum.ALE).price(new BigDecimal("2.99"))
				.upc("1223l").build();
	}
}
