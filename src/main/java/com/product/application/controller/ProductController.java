package com.product.application.controller;

import java.net.URI;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.domain.data.ProductDto;
import com.product.domain.ports.api.ProductServicePort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/product")
@RestController
public class ProductController {

	private final ProductServicePort productServicePort;
	
	@Autowired
	public ProductController(ProductServicePort productServicePort) {
		this.productServicePort = productServicePort;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto) {
		log.info("##ProductController.save begin {}", new Date());
		ProductDto productSaved = productServicePort.save(productDto);
		log.info("##ProductController.save end {}", new Date());
		return ResponseEntity.created(URI.create("/product")).body(productSaved);
	}
	
	@PutMapping(path = "/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> update(@NotNull @NotBlank @PathVariable(value = "productId") Long productId,
			@Valid @RequestBody ProductDto productDto) {
		log.info("##ProductController.update begin {}", new Date());
		ProductDto productUpdated = productServicePort.update(productId, productDto);
		log.info("##ProductController.update end {}", new Date());
		return ResponseEntity.ok().body(productUpdated);
	}
	
	@GetMapping(path = "/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDto> get(@NotNull @NotBlank @PathVariable(value = "productId") Long productId) {
		log.info("##ProductController.get begin {}", new Date());
		ProductDto product = productServicePort.get(productId);
		log.info("##ProductController.get end {}", new Date());
		return ResponseEntity.ok().body(product);
	}
	
}
