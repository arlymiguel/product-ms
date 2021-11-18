package com.product.domain;

import org.springframework.stereotype.Service;

import com.product.domain.data.ProductDto;
import com.product.domain.ports.api.ProductServicePort;
import com.product.domain.ports.spi.ProductPersistancePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductServicePort {

	private final ProductPersistancePort productPersistancePort;
	
	@Override
	public ProductDto save(ProductDto product) {
		log.info("save: {}", product);
		return productPersistancePort.save(product);
	}

	@Override
	public ProductDto update(long id, ProductDto product) {
		log.info("update: {}, {}",id, product);
		return productPersistancePort.update(id, product);
	}

	@Override
	public ProductDto get(long id) {
		log.info("get: {}", id);
		return productPersistancePort.get(id);
	}

}
