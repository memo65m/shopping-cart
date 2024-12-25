package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartProductEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartProductEntityMapper {
    CartProductEntity modelToEntity(CartProduct cartProduct);
    CartProduct entityToModel(CartProductEntity cartProductEntity);
}
