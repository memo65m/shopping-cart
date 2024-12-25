package com.indra.shoppingcart.infrastructure.adapter.input.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CartMapper {

    private final ModelMapper modelMapper;

    public CartResponse cartToCartResponse(Cart cart) {
        return modelMapper.map(cart, CartResponse.class);
    }
}
