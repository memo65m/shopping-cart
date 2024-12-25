package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.CartProduct;

public interface CartProductRepository {
    void createCartProduct(CartProduct cartProduct);
    Boolean existsCartProduct(Integer cardId, Integer productId);
    void updateQuantity(CartProduct cartProduct);
}
