package com.indra.shoppingcart.domain.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.domain.model.Cart;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public Cart getCart(Integer cartId) {
        return cartRepository.getCartById(cartId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createCart(Integer userId) {
        Boolean exists = cartRepository.existsCartByUserId(userId);
        if (Boolean.FALSE.equals(exists)) {
            cartRepository.createCart(userId);
        }
    }

}
