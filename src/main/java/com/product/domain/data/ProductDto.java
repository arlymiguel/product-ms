package com.product.domain.data;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDto {

	private long id;
	
	@NotBlank
    @NotNull
	private String description;
	
	private String imageUrl;
	private String imageUrlCached;
	private String logoCached;
	
    @NotNull
	private Double price;
	
    private List<DetailDto> details;
	
}
