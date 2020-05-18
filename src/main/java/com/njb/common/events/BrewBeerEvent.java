package com.njb.common.events;

import com.njb.msscbeerservice.web.model.BeerDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
	private static final long serialVersionUID = -8302503696673422884L;

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}

}
