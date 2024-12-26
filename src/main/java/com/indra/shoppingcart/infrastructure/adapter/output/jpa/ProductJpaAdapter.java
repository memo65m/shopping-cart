package com.indra.shoppingcart.infrastructure.adapter.output.jpa;

import com.indra.shoppingcart.application.ports.output.ProductRepository;
import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.Product;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.ProductEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.ProductEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.ProductJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJpaAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductEntityMapper productEntityMapper;

    public Product getProductById(Integer productId) {
        ProductEntity product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return productEntityMapper.entityToModel(product);
    }

}
