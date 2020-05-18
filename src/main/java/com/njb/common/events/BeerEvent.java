package com.njb.common.events;

import java.io.Serializable;

import com.njb.msscbeerservice.web.model.BeerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerEvent implements Serializable {

	private static final long serialVersionUID = 5360586042332335592L;

	private BeerDto beerDto;

}
