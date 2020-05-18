package com.njb.msscbeerservice.events;

import com.njb.msscbeerservice.web.model.BeerDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

	private static final long serialVersionUID = 7692569709795759863L;

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}

}
