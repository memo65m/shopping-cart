package com.indra.shoppingcart.domain.service;

import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.domain.model.Cart;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCart(Integer cartId) {
        return cartRepository.getCartById(cartId);
    }

}
