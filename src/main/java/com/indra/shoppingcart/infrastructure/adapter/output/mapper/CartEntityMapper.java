package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CartEntityMapper {

    private final ModelMapper modelMapper;

    public Cart entityToModel(CartEntity cartEntity) {
        return modelMapper.map(cartEntity, Cart.class);
    }
}
