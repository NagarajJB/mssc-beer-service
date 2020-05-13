package com.njb.msscbeerservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.njb.msscbeerservice.domain.Beer;
import com.njb.msscbeerservice.web.model.BeerDto;

@Mapper(uses = { DateMapper.class })
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

	BeerDto beerToBeerDto(Beer beer);

	BeerDto beerToBeerDtoWithInventory(Beer beer);

	Beer beerDtoToBeer(BeerDto dto);
}
