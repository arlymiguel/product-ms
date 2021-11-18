package com.product.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.product.infrastructure.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

}
