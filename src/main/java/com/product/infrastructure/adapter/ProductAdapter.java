package com.product.infrastructure.adapter;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.product.domain.data.DataCachedDto;
import com.product.domain.data.ProductDto;
import com.product.domain.ports.spi.ProductPersistancePort;
import com.product.infrastructure.entity.Detail;
import com.product.infrastructure.entity.Product;
import com.product.infrastructure.exception.custom.ProductNotFoundException;
import com.product.infrastructure.mapper.ProductMapper;
import com.product.infrastructure.provider.products.ProductClient;
import com.product.infrastructure.provider.products.response.ProductResponse;
import com.product.infrastructure.repository.DetailRepository;
import com.product.infrastructure.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAdapter implements ProductPersistancePort {

	private final ProductRepository productRepository;
	private final DetailRepository detailRepository;
	private final ProductClient productClient;
	private final ProductMapper productMapper;
	
	@Override
	public ProductDto save(ProductDto productDto) {
		Product productSaved = productRepository.save(productMapper.getProductFromProductDto(productDto));
		
		if (null!= productDto.getDetails() && productDto.getDetails().size()>0) {
			productDto.getDetails().stream().forEach(detail ->{
				Detail detailToSave = productMapper.getDetailFromDetailDto(detail);
				detailToSave.setProduct(productSaved);
				detailRepository.save(detailToSave);
			});
		}
		return get(productSaved.getId());
	}

	@Override
	public ProductDto update(long id, ProductDto productDto) {
		Optional<Product> productFound = productRepository.findById(id);
		if(productFound.isPresent()) {
			
			Product productToUpdate = productMapper.getProducFromProduct(productFound.get(),productDto);
			Product productSaved = productRepository.save(productToUpdate);
			
			if (null!= productDto.getDetails() && productDto.getDetails().size()>0) {
				productDto.getDetails().stream().forEach(detail ->{
					
					Optional<Detail> detailFound = detailRepository.findById(detail.getId());
					if (detailFound.isPresent()) {
						Detail detailToSave = productMapper.getDetailFromDetailDto(detailFound.get(), detail);
						detailRepository.save(detailToSave);
					} else {
						Detail detailToSave = productMapper.getDetailFromDetailDto(detail);
						detailToSave.setProduct(productSaved);
						detailRepository.save(detailToSave);
					}
					
				});
			}
			return get(productSaved.getId());
		} else {
			throw new ProductNotFoundException("There is any product with the id provided");
		}
		
	}
	
	@Cacheable("dataCached")
	private DataCachedDto getFromCache() {
		byte[] array = new byte[7];
	    new Random().nextBytes(array);
	    String image = new String(array, Charset.forName("UTF-8"));
	    String logo = new String(array, Charset.forName("UTF-8"));
	    
	    return DataCachedDto.builder().image(image).logo(logo).build();
	}

	@Override
	public ProductDto get(long id) {
		Optional<Product> productFound = productRepository.findById(id);
		if (productFound.isPresent()) {
			String imageUrl = "";
			try {
				ProductResponse productResponse = productClient.getProductImage(id);
				imageUrl = productResponse.getImageURL();
			} catch (Exception e) {
				log.error("", e);
			}
			 
			
			 Product product = productFound.get();
			 ProductDto productDto = ProductDto.builder()
					 .id(product.getId())
					 .description(product.getDescription())
					 .imageUrl(imageUrl)
					 .imageUrlCached(getFromCache().getImage())
					 .logoCached(getFromCache().getLogo())
					 .price(product.getPrice())
					 .details( detailRepository.findByProduct(product, Sort.unsorted()).stream().map(productMapper::getDetailDtoFromDetail).collect(Collectors.toList()) )
					 .build();
			 return productDto;
		}
		
		return null;
	}

}
