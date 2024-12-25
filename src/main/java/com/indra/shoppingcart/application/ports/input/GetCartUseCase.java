package com.indra.shoppingcart.application.ports.input;

import com.indra.shoppingcart.domain.model.Cart;

public interface GetCartUseCase {
    Cart execute(Integer cartId);
}
