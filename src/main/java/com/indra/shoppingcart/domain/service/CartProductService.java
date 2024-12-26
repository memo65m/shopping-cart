package com.indra.shoppingcart.domain.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indra.shoppingcart.application.ports.output.CartProductRepository;
import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.application.ports.output.ProductRepository;
import com.indra.shoppingcart.domain.exception.BadRequestException;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.domain.model.Product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartProductService {

    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CartProduct createCartProduct(CartProduct cartProduct) {

        Product product = productRepository.getProductById(cartProduct.getProduct().getId());
        Cart cart = cartRepository.getCartByUserId(cartProduct.getCart().getUser().getId());
        Boolean existsCartProduct = cartProductRepository.existsCartProduct(cart.getId(),
                cartProduct.getProduct().getId());
        cartProduct.setCart(cart);

        if (cartProduct.getQuantity() > product.getStock()) {
            throw new BadRequestException("Stock insuficiente");
        }

        if (Boolean.TRUE.equals(existsCartProduct)) {
            cartProduct = cartProductRepository.updateQuantity(cartProduct);
        } else {
            cartProduct = cartProductRepository.createCartProduct(cartProduct);
        }

        return cartProduct;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCartProduct(Integer cartProductId, Integer userId) {
        cartProductRepository.deleteCartProduct(cartProductId, userId);
    }

}
