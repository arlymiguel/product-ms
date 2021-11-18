package com.product.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.domain.data.DetailDto;
import com.product.domain.data.ProductDto;
import com.product.domain.ports.api.ProductServicePort;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	private ProductController productController;

	@Mock
	private ProductServicePort productServicePort;

	@Autowired
	private MockMvc mockMvc;
	
	ProductDto productDto;
	ProductDto productDtoToSave;
	DetailDto detailDto;
	List<DetailDto> detailsDtoList;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productServicePort)).build();
		
		detailDto = new DetailDto().builder().key("Key1").value("Value1").build();
		
		detailsDtoList = new ArrayList<>();
		detailsDtoList.add(detailDto);
		
		productDto = ProductDto.builder().id(1L).description("Description").imageUrl("url")
				.imageUrlCached("image cached").logoCached("logo cached").price(50.50).details(detailsDtoList).build();
		
		productDtoToSave = ProductDto.builder().description("Description").price(50.50).details(detailsDtoList).build();
	}

	@Test
	void save_Test() throws Exception {

		when(productServicePort.save(any(ProductDto.class))).thenReturn(any(ProductDto.class));

	    mockMvc.perform(post("/product")
	      .content(asJsonString(productDto))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated());
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
