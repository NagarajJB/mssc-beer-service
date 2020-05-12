package com.njb.msscbeerservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.njb.msscbeerservice.web.model.BeerDto;

@ActiveProfiles("snake")
@JsonTest
public class BeerDtoSnakeTest extends BaseTest {

	@Autowired
	ObjectMapper mapper;

	@Test
	void testSnake() throws JsonProcessingException {
		BeerDto dto = getValidBeerDto();

		System.out.println(mapper.writeValueAsString(dto));

	}
}
