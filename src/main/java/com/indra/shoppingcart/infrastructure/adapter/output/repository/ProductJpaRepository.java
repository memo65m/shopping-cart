package com.indra.shoppingcart.infrastructure.adapter.output.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.shoppingcart.infrastructure.adapter.output.entity.ProductEntity;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Integer> {
}
