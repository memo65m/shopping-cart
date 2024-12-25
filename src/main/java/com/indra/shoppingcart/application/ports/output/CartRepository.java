package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.Cart;

public interface CartRepository {
    Cart getCartById(Integer cartId);
    Boolean existsCartByUserId(Integer userId);
    void createCart(Integer userId);
}
