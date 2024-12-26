package com.indra.shoppingcart.application.ports.input;

import com.indra.shoppingcart.domain.model.CartProduct;

public interface UpdateCartProductUseCase {
    String execute(CartProduct cartProduct);
}
