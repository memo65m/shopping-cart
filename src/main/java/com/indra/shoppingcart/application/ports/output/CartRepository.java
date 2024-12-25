package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.Cart;

public interface CartRepository {
    Cart getCartById(Integer cartId);
}
