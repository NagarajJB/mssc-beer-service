package com.njb.msscbeerservice;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njb.model.BeerDto;
import com.njb.model.BeerStyleEnum;
import com.njb.msscbeerservice.services.BeerService;
import com.njb.msscbeerservice.web.controller.BeerController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.njb.com", uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerService beerService;

	@Test
	void getBeerById() throws Exception {

		given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());

		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString()).param("isCold", "yes")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(document("v1/beer-get",
						pathParameters(parameterWithName("beerId").description("UUID of desired beer to get.")),
						requestParameters(parameterWithName("isCold").description("example of query param")),
						responseFields(fieldWithPath("id").description("Id of Beer").type(UUID.class),
								fields.withPath("version").description("Version number").type(String.class),
								fields.withPath("createdDate").description("Date Created"),
								fields.withPath("lastModifiedDate").description("Date Updated"),
								fields.withPath("beerName").description("Beer Name"),
								fields.withPath("beerStyle").description("Beer Style"),
								fields.withPath("upc").description("UPC of Beer"),
								fields.withPath("price").description("Price"),
								fields.withPath("quantityOnHand").description("Quantity On hand"))));

	}

	@Test
	void saveNewBeer() throws Exception {

		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);

		given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

		mockMvc.perform(post("/api/v1/beer/").contentType(MediaType.APPLICATION_JSON).content(beerDtoJson))
				.andExpect(status().isCreated())
				.andDo(document("v1/beer-new",
						requestFields(fields.withPath("id").ignored(), fields.withPath("version").ignored(),
								fields.withPath("createdDate").ignored(), fields.withPath("lastModifiedDate").ignored(),
								fields.withPath("beerName").description("Name of the beer"),
								fields.withPath("beerStyle").description("Style of Beer"),
								fields.withPath("upc").description("Beer UPC").attributes(),
								fields.withPath("price").description("Beer Price"),
								fields.withPath("quantityOnHand").ignored())));
	}

	@Test
	void updateBeerById() throws Exception {
		String beerDtoJson = objectMapper.writeValueAsString(getValidBeerDto());

		mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson)).andExpect(status().isNoContent());
	}

	BeerDto getValidBeerDto() {
		return BeerDto.builder().beerName("My Beer").beerStyle(BeerStyleEnum.ALE).price(new BigDecimal("2.99"))
				.upc("1223l").build();
	}

	private static class ConstrainedFields {

		private final ConstraintDescriptions constraintDescriptions;

		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}

		private FieldDescriptor withPath(String path) {
			return fieldWithPath(path).attributes(key("constraints").value(StringUtils
					.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
		}
	}
}