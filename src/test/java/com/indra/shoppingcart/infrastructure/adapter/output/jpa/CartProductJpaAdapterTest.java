package com.indra.shoppingcart.infrastructure.adapter.output.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.domain.model.Product;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartProductEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartProductEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartProductJpaRepository;

@ExtendWith(MockitoExtension.class)
class CartProductJpaAdapterTest {

    @Mock
    private CartProductJpaRepository cartProductJpaRepository;

    @Mock
    private CartProductEntityMapper cartProductEntityMapper;

    @InjectMocks
    private CartProductJpaAdapter cartProductJpaAdapter;

    @Test
    void testCreateCartProduct() {
        CartProduct cartProduct = new CartProduct();
        CartProductEntity cartProductEntity = new CartProductEntity();

        when(cartProductEntityMapper.modelToEntity(cartProduct)).thenReturn(cartProductEntity);
        when(cartProductJpaRepository.save(cartProductEntity)).thenReturn(cartProductEntity);
        when(cartProductEntityMapper.entityToModel(cartProductEntity)).thenReturn(cartProduct);

        CartProduct result = cartProductJpaAdapter.createCartProduct(cartProduct);

        assertEquals(cartProduct, result);
        verify(cartProductEntityMapper).modelToEntity(cartProduct);
        verify(cartProductJpaRepository).save(cartProductEntity);
        verify(cartProductEntityMapper).entityToModel(cartProductEntity);
    }

    @Test
    void testExistsCartProduct() {
        Integer cartId = 1;
        Integer productId = 1;

        when(cartProductJpaRepository.existsByCart_IdAndProduct_Id(cartId, productId)).thenReturn(true);

        Boolean result = cartProductJpaAdapter.existsCartProduct(cartId, productId);

        assertTrue(result);
        verify(cartProductJpaRepository).existsByCart_IdAndProduct_Id(cartId, productId);
    }

    @Test
    void testUpdateQuantity() {
        CartProductEntity cartProductEntity = new CartProductEntity();
        CartProduct cartProduct = CartProduct.builder()
                .cart(Cart.builder().id(1).build())
                .product(Product.builder().id(1).build())
                .quantity(10)
                .build();

        when(cartProductJpaRepository.findByCart_IdAndProduct_Id(cartProduct.getCart().getId(),
                cartProduct.getProduct().getId()))
                .thenReturn(Optional.of(cartProductEntity));
        when(cartProductJpaRepository.save(cartProductEntity)).thenReturn(cartProductEntity);
        when(cartProductEntityMapper.entityToModel(cartProductEntity)).thenReturn(cartProduct);

        CartProduct result = cartProductJpaAdapter.updateQuantity(cartProduct);

        assertEquals(cartProduct, result);
        verify(cartProductJpaRepository).findByCart_IdAndProduct_Id(cartProduct.getCart().getId(),
                cartProduct.getProduct().getId());
        verify(cartProductJpaRepository).save(cartProductEntity);
        verify(cartProductEntityMapper).entityToModel(cartProductEntity);
    }

    @Test
    void testUpdateQuantity_NotFound() {
        CartProduct cartProduct = CartProduct.builder()
                .cart(Cart.builder().id(1).build())
                .product(Product.builder().id(1).build())
                .quantity(10)
                .build();

        when(cartProductJpaRepository.findByCart_IdAndProduct_Id(cartProduct.getCart().getId(),
                cartProduct.getProduct().getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartProductJpaAdapter.updateQuantity(cartProduct));
        verify(cartProductJpaRepository).findByCart_IdAndProduct_Id(cartProduct.getCart().getId(),
                cartProduct.getProduct().getId());
    }

    @Test
    void testDeleteCartProduct() {
        Integer cartProductId = 1;
        Integer userId = 1;
        CartProductEntity cartProductEntity = new CartProductEntity();

        when(cartProductJpaRepository.findByIdAndCart_User_Id(cartProductId, userId))
                .thenReturn(Optional.of(cartProductEntity));

        cartProductJpaAdapter.deleteCartProduct(cartProductId, userId);

        verify(cartProductJpaRepository).findByIdAndCart_User_Id(cartProductId, userId);
        verify(cartProductJpaRepository).deleteById(cartProductEntity.getId());
    }

    @Test
    void testDeleteCartProduct_NotFound() {
        Integer cartProductId = 1;
        Integer userId = 1;

        when(cartProductJpaRepository.findByIdAndCart_User_Id(cartProductId, userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartProductJpaAdapter.deleteCartProduct(cartProductId, userId));
        verify(cartProductJpaRepository).findByIdAndCart_User_Id(cartProductId, userId);
    }

    @Test
    void testGetCartProductById() {
        Integer cartProductId = 1;
        Integer userId = 1;
        CartProductEntity cartProductEntity = new CartProductEntity();
        CartProduct cartProduct = new CartProduct();

        when(cartProductJpaRepository.findByIdAndCart_User_Id(cartProductId, userId)).thenReturn(Optional.of(cartProductEntity));
        when(cartProductEntityMapper.entityToModel(cartProductEntity)).thenReturn(cartProduct);

        CartProduct result = cartProductJpaAdapter.getCartProductById(cartProductId, userId);

        assertEquals(cartProduct, result);
        verify(cartProductJpaRepository).findByIdAndCart_User_Id(cartProductId, userId);
        verify(cartProductEntityMapper).entityToModel(cartProductEntity);
    }

    @Test
    void testGetCartProductById_NotFound() {
        Integer cartProductId = 1;
        Integer userId = 1;

        when(cartProductJpaRepository.findByIdAndCart_User_Id(cartProductId, userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartProductJpaAdapter.getCartProductById(cartProductId, userId));
        verify(cartProductJpaRepository).findByIdAndCart_User_Id(cartProductId, userId);
    }

    @Test
    void testUpdateQuantityById() {
        Integer cartProductId = 1;
        Integer quantity = 10;
        CartProductEntity cartProductEntity = new CartProductEntity();

        when(cartProductJpaRepository.findById(cartProductId)).thenReturn(Optional.of(cartProductEntity));

        cartProductJpaAdapter.updateQuantity(cartProductId, quantity);

        assertEquals(quantity, cartProductEntity.getQuantity());
        verify(cartProductJpaRepository).findById(cartProductId);
        verify(cartProductJpaRepository).save(cartProductEntity);
    }

    @Test
    void testUpdateQuantityById_NotFound() {
        Integer cartProductId = 1;
        Integer quantity = 10;

        when(cartProductJpaRepository.findById(cartProductId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartProductJpaAdapter.updateQuantity(cartProductId, quantity));
        verify(cartProductJpaRepository).findById(cartProductId);
    }
}
