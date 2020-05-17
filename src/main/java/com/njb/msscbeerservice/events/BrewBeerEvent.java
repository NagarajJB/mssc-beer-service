package com.njb.msscbeerservice.events;

import com.njb.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {
	private static final long serialVersionUID = -8302503696673422884L;

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
		// TODO Auto-generated constructor stub
	}

}
