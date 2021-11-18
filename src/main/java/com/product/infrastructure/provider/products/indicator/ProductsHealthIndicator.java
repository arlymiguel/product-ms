package com.product.infrastructure.provider.products.indicator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.product.infrastructure.provider.products.ProductClient;
import com.product.infrastructure.provider.products.response.ProductResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductsHealthIndicator implements HealthIndicator {
	
	@Autowired
	private ProductClient productClient;
	
	@Override
	public Health health() {
		
		Health.Builder statusBuilder = Health.up();
		
		Map productEndpointHealthResponse = productEndpointHealth();
		
		if (!Boolean.parseBoolean(productEndpointHealthResponse.get("avalilable").toString())) {
			statusBuilder = Health.down().withDetails(productEndpointHealthResponse);
		}
		
		return statusBuilder.withDetails(productEndpointHealthResponse).build();
	}

	private Map productEndpointHealth() {
		try {
			
			ProductResponse productImage = productClient.getProductImage(1L);
			
			Map<String, Object> details = new HashMap<>();
			details.put("avalilable", true);
			details.put("detail",  "Running");
			return details;
			
		} catch (Exception e) {
			log.info("ProductsHealthIndicator.productEndpointHealth:{}", e);
			Map<String, Object> details = new HashMap<>();
			details.put("avalilable", false);
			details.put("detail", e.getMessage());
			return details;
		}
		
	}

}
