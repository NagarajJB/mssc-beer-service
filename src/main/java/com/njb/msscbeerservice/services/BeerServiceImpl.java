package com.njb.msscbeerservice.services;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.njb.msscbeerservice.domain.Beer;
import com.njb.msscbeerservice.repositories.BeerRepository;
import com.njb.msscbeerservice.web.controller.NotFoundException;
import com.njb.msscbeerservice.web.mappers.BeerMapper;
import com.njb.msscbeerservice.web.model.BeerDto;
import com.njb.msscbeerservice.web.model.BeerPagedList;
import com.njb.msscbeerservice.web.model.BeerStyleEnum;

@Service
public class BeerServiceImpl implements BeerService {

	@Autowired
	private BeerRepository beerRepository;

	@Autowired
	private BeerMapper beerMapper;

	@Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand==false")
	@Override
	public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
		if (showInventoryOnHand)
			return beerMapper
					.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
		else
			return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
	}

	@Cacheable(cacheNames = "beerUpcCache")
	@Override
	public BeerDto getByUpc(String upc) {
		return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle().name());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());

		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand==false")
	@Override
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
			Boolean showInventoryOnHand) {

		BeerPagedList beerPagedList;
		Page<Beer> beerPage;

		if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
		} else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
		} else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
		} else {
			beerPage = beerRepository.findAll(pageRequest);
		}

		if (showInventoryOnHand) {
			beerPagedList = new BeerPagedList(
					beerPage.getContent().stream().map(beerMapper::beerToBeerDtoWithInventory)
							.collect(Collectors.toList()),
					PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
					beerPage.getPageable().getPageSize());
		} else {
			beerPagedList = new BeerPagedList(
					beerPage.getContent().stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList()),
					PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
					beerPage.getPageable().getPageSize());
		}

		return beerPagedList;
	}
}