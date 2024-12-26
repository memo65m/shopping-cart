package com.indra.shoppingcart.application.ports.input;

public interface DeleteCartProductUseCase {
    void execute(Integer cartProductId, Integer userId);
}
