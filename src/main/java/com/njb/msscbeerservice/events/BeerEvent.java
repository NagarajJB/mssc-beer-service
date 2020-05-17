package com.njb.msscbeerservice.events;

import java.io.Serializable;

import com.njb.msscbeerservice.web.model.BeerDto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BeerEvent implements Serializable {

	private static final long serialVersionUID = 5360586042332335592L;

	private final BeerDto beerDto;

}
