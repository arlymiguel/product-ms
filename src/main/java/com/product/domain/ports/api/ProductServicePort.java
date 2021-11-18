package com.product.domain.ports.api;

import com.product.domain.data.ProductDto;

public interface ProductServicePort {

	ProductDto save(ProductDto product);
	ProductDto update(long id, ProductDto product);
	ProductDto get(long id);
	
}
