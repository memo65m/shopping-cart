package com.indra.shoppingcart.application.ports.input;

import com.indra.shoppingcart.domain.model.CartProduct;

public interface CreateCartProductUseCase {
    CartProduct execute(CartProduct cartProduct);
}
