package com.indra.shoppingcart.application.ports.input;

import com.indra.shoppingcart.domain.model.CartProduct;

public interface CreateCartProductUseCase {
    void execute(CartProduct cartProduct);
}
