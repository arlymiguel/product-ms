package com.product.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.product.infrastructure.entity.Detail;
import com.product.infrastructure.entity.Product;

@Repository
public interface DetailRepository extends CrudRepository<Detail, Long> {

	List<Detail> findByProduct(Product product, Sort sort);
	
}
