package com.indra.shoppingcart.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indra.shoppingcart.application.ports.output.CartProductRepository;
import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.application.ports.output.ProductRepository;
import com.indra.shoppingcart.domain.exception.BadRequestException;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.domain.model.Product;
import com.indra.shoppingcart.domain.model.User;

@ExtendWith(MockitoExtension.class)
public class CartProductServiceTest {

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartProductService cartProductService;

    @Test
    public void testCreateCartProduct() {

        Cart cart = Cart.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .build();

        Product product = Product.builder()
                .id(1)
                .stock(10)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .quantity(5)
                .cart(cart)
                .build();

        when(productRepository.getProductById(any())).thenReturn(product);
        when(cartRepository.getCartByUserId(any())).thenReturn(cart);
        when(cartProductRepository.existsCartProduct(anyInt(), anyInt())).thenReturn(false);
        when(cartProductRepository.createCartProduct(any())).thenReturn(cartProduct);

        CartProduct result = cartProductService.createCartProduct(cartProduct);

        assertEquals(cartProduct, result);
        verify(cartProductRepository, times(1)).createCartProduct(cartProduct);
    }

    @Test
    public void testCreateCartProductWithInsufficientStock() {

        Cart cart = Cart.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .build();

        Product product = Product.builder()
                .id(1)
                .stock(5)
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .quantity(10)
                .cart(cart)
                .build();

        when(productRepository.getProductById(any())).thenReturn(product);
        when(cartRepository.getCartByUserId(any())).thenReturn(cart);
        when(cartProductRepository.existsCartProduct(anyInt(), anyInt())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            cartProductService.createCartProduct(cartProduct);
        });
    }

    @Test
    public void testDeleteCartProduct() {
        doNothing().when(cartProductRepository).deleteCartProduct(anyInt(), anyInt());

        cartProductService.deleteCartProduct(1, 1);

        verify(cartProductRepository, times(1)).deleteCartProduct(1, 1);
    }

    @Test
    public void testUpdateCartProduct() {

        Cart cart = Cart.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .id(1)
                .quantity(5)
                .cart(cart)
                .build();

        Product product = Product.builder()
                .stock(10)
                .build();

        CartProduct cartProductDB = CartProduct.builder()
                .id(1)
                .quantity(5)
                .product(product)
                .build();

        when(cartProductRepository.getCartProductById(anyInt())).thenReturn(cartProductDB);
        doNothing().when(cartProductRepository).updateQuantity(anyInt(), anyInt());

        String result = cartProductService.updateCartProduct(cartProduct);

        assertEquals("Cantidad actualizada", result);
        verify(cartProductRepository, times(1)).updateQuantity(anyInt(), anyInt());
    }

    @Test
    public void testUpdateCartProductWithZeroQuantity() {

        Cart cart = Cart.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .build();

        CartProduct cartProduct = CartProduct.builder()
                .id(1)
                .quantity(0)
                .cart(cart)
                .build();

        Product product = Product.builder()
                .stock(10)
                .build();

        CartProduct cartProductDB = CartProduct.builder()
                .id(1)
                .quantity(5)
                .product(product)
                .build();

        when(cartProductRepository.getCartProductById(anyInt())).thenReturn(cartProductDB);
        doNothing().when(cartProductRepository).deleteCartProduct(anyInt(), anyInt());

        String result = cartProductService.updateCartProduct(cartProduct);

        assertEquals("Produto removido del carrito", result);
        verify(cartProductRepository, times(1)).deleteCartProduct(any(), any());
    }
}
