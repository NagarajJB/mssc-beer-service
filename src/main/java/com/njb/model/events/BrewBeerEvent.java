package com.njb.model.events;

import com.njb.model.BeerDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
	private static final long serialVersionUID = -8302503696673422884L;

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}

}
