package com.indra.shoppingcart.infrastructure.adapter.input.mapper;

import org.mapstruct.Mapper;

import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartResponse;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse cartToCartResponse(Cart cart);
}
