package com.product.infrastructure.provider.products;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.product.infrastructure.provider.products.response.ProductResponse;

@FeignClient(url = "${provider.products.host}", value = "products-ms")
public interface ProductClient {

	@GetMapping("${provider.products.path}/{productId}")
	public ProductResponse getProductImage(@PathVariable("productId") Long productId);

}
