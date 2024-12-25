package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.mapstruct.Mapper;

import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartEntity;

@Mapper(componentModel = "spring")
public interface CartEntityMapper {
    Cart entityToModel(CartEntity cartEntity);
}
