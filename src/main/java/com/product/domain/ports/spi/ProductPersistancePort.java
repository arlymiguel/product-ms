package com.product.domain.ports.spi;

import com.product.domain.data.ProductDto;

public interface ProductPersistancePort {

	ProductDto save(ProductDto product);
	ProductDto update(long id, ProductDto product);
	ProductDto get(long id);
	
}
