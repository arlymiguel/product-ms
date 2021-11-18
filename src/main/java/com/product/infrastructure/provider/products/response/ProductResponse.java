package com.product.infrastructure.provider.products.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductResponse {

	private Long id;
	private String imageURL;
	
}
