package com.product.infrastructure.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.product.domain.data.DetailDto;
import com.product.domain.data.ProductDto;
import com.product.infrastructure.entity.Detail;
import com.product.infrastructure.entity.Product;

@Component
public class ProductMapper {
	
	public Product getProducFromProduct(Product product, ProductDto productDto) {
		
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		return product;
		
	}
	
	public ProductDto getProducDtoFromProduct(Product product) {
		return ProductDto.builder()
				.id(product.getId())
				.description(product.getDescription())
				.price(product.getPrice())
				.details(product.getDetails().stream().map(detail->getDetailDtoFromDetail(detail)).collect(Collectors.toList())).build();
		
	}
	
	public Product getProductFromProductDto(ProductDto productDto) {
		return Product.builder()
				.id(productDto.getId())
				.description(productDto.getDescription())
				.price(productDto.getPrice())
				.build();
				
	}
	
	
	public DetailDto getDetailDtoFromDetail(Detail detail) {
		return DetailDto.builder().id(detail.getId()).key(detail.getKey()).value(detail.getValue()).build();
	}
	
	public Detail getDetailFromDetailDto(DetailDto detail) {
		return Detail.builder().key(detail.getKey()).value(detail.getValue()).build();
	}

	public Detail getDetailFromDetailDto(Detail detail, DetailDto detailDto) {
		detail.setKey(detailDto.getKey());
		detail.setValue(detailDto.getValue());
		return detail;
	}
	
}
