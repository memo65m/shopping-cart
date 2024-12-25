package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.mapstruct.Mapper;

import com.indra.shoppingcart.domain.model.Product;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    Product entityToModel(ProductEntity productEntity);
}
