package com.product.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.product.domain.data.DetailDto;
import com.product.domain.data.ProductDto;
import com.product.infrastructure.entity.Detail;
import com.product.infrastructure.entity.Product;
import com.product.infrastructure.mapper.ProductMapper;
import com.product.infrastructure.provider.products.ProductClient;
import com.product.infrastructure.provider.products.response.ProductResponse;
import com.product.infrastructure.repository.DetailRepository;
import com.product.infrastructure.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductAdapterTest {

	private ProductAdapter productAdapter;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private DetailRepository detailRepository;

	@Mock
	private ProductClient productClient;

	@Mock
	private ProductMapper productMapper;

	ProductDto productDto;
	Product product;
	DetailDto detailDto;
	Detail detail;
	Optional<Detail> detailOptional;
	Optional<Product> productOptional;
	ProductResponse productResponse;
	List<Detail> detailsList;
	List<DetailDto> detailsDtoList;

	@BeforeEach
	void setup() {
		productAdapter = new ProductAdapter(productRepository, detailRepository, productClient, productMapper);

		detailDto = new DetailDto().builder().key("Key1").value("Value1").build();
		detail = new Detail().builder().key("Key1").value("Value1").build();
		
		detailOptional = Optional.of(detail);
		
		detailsDtoList = new ArrayList<>();
		detailsDtoList.add(detailDto);
		
		detailsList = new ArrayList<>();
		detailsList.add(detail);
		
		productDto = ProductDto.builder().id(1L).description("Description").imageUrl("url")
				.imageUrlCached("image cached").logoCached("logo cached").price(50.50).details(detailsDtoList).build();
		
		product = Product.builder().id(1L).description("Description").price(50.50).build();
		productOptional = Optional.of(product);
		
		productResponse = ProductResponse.builder().id(1L).imageURL("Image").build();

	}

	@Test
	void save_Test() {
		Detail detailSaved = new Detail().builder().key("Key1").value("Value1").build();
		
		//save
		when(productMapper.getProductFromProductDto(productDto)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(productMapper.getDetailFromDetailDto(detailDto)).thenReturn(detail);
		when(detailRepository.save(detail)).thenReturn(detailSaved);
		
		//get
		when(productRepository.findById(product.getId())).thenReturn(productOptional);
		when(productClient.getProductImage(product.getId())).thenReturn(productResponse);
		when(productMapper.getDetailDtoFromDetail(detail)).thenReturn(detailDto);
		when(detailRepository.findByProduct(product, Sort.unsorted())).thenReturn(detailsList);
		
		
		ProductDto productDtoSavedResponse = productAdapter.save(productDto);
		
		assertEquals(productDtoSavedResponse.getId(), product.getId());
	}
	
	@Test
	void update_Test() {
		
		//update
		when(productRepository.findById(product.getId())).thenReturn(productOptional);
		when(productMapper.getProducFromProduct(productOptional.get(), productDto)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(detailRepository.findById(detail.getId())).thenReturn(detailOptional);
		when(productMapper.getDetailFromDetailDto(detailOptional.get(), detailDto)).thenReturn(detail);
		
		
		//get
		when(productRepository.findById(product.getId())).thenReturn(productOptional);
		when(productClient.getProductImage(product.getId())).thenReturn(productResponse);
		when(productMapper.getDetailDtoFromDetail(detail)).thenReturn(detailDto);
		when(detailRepository.findByProduct(product, Sort.unsorted())).thenReturn(detailsList);
		
		
		ProductDto productDtoSavedResponse = productAdapter.update(productDto.getId(), productDto);
		
		assertEquals(productDtoSavedResponse.getId(), product.getId());
	}
	
	@Test
	void get_Test() {
		
		//get
		when(productRepository.findById(product.getId())).thenReturn(productOptional);
		when(productClient.getProductImage(product.getId())).thenReturn(productResponse);
		when(productMapper.getDetailDtoFromDetail(detail)).thenReturn(detailDto);
		when(detailRepository.findByProduct(product, Sort.unsorted())).thenReturn(detailsList);
		
		
		ProductDto productDtoGotResponse = productAdapter.get(product.getId());
		
		assertEquals(productDtoGotResponse.getId(), product.getId());
	}

}
