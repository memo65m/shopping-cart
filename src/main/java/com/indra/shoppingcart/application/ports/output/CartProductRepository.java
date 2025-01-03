package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.CartProduct;

public interface CartProductRepository {
    CartProduct createCartProduct(CartProduct cartProduct);
    Boolean existsCartProduct(Integer cardId, Integer productId);
    CartProduct updateQuantity(CartProduct cartProduct);
    void updateQuantity(Integer cartProductId, Integer quantity);
    void deleteCartProduct(Integer cartProductId, Integer userId);
    CartProduct getCartProductById(Integer cartProductId, Integer userId);
}
