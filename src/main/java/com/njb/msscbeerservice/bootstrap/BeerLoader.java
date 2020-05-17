package com.njb.msscbeerservice.bootstrap;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.njb.msscbeerservice.domain.Beer;
import com.njb.msscbeerservice.repositories.BeerRepository;
import com.njb.msscbeerservice.web.model.BeerStyleEnum;

//@Component
public class BeerLoader implements CommandLineRunner {

	public static final String BEER_1_UPC = "0631234200036";
	public static final String BEER_2_UPC = "0631234300019";
	public static final String BEER_3_UPC = "0083783375213";

	@Autowired
	private BeerRepository beerRepository;

	public BeerLoader(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (beerRepository.count() == 0)
			loadBeerObjects();
	}

	private void loadBeerObjects() {
		if (beerRepository.count() == 0) {

			beerRepository.save(Beer.builder().beerName("Mango Bobs").beerStyle(BeerStyleEnum.IPA.name())
					.quantityToBrew(200).minOnHand(12).upc(BEER_1_UPC).price(new BigDecimal("12.95")).build());

			beerRepository.save(Beer.builder().beerName("Galaxy Cat").beerStyle("PALE_ALE").quantityToBrew(200)
					.minOnHand(12).upc(BEER_2_UPC).price(new BigDecimal("11.95")).build());

			beerRepository.save(Beer.builder().beerName("Pinball Portner").beerStyle("PALE_ALE").quantityToBrew(200)
					.minOnHand(12).upc(BEER_3_UPC).price(new BigDecimal("11.95")).build());
		}
	}
}
