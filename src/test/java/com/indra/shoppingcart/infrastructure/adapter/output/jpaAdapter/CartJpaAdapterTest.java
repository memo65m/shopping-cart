package com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
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
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.UserEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartJpaRepository;

@ExtendWith(MockitoExtension.class)
public class CartJpaAdapterTest {

    @Mock
    private CartJpaRepository cartJpaRepository;

    @Mock
    private CartEntityMapper cartEntityMapper;

    @InjectMocks
    private CartJpaAdapter cartJpaAdapter;

    @Test
    public void testExistsCartByUserId() {
        Integer userId = 1;
        when(cartJpaRepository.existsByUser_Id(userId)).thenReturn(true);

        Boolean result = cartJpaAdapter.existsCartByUserId(userId);

        assertTrue(result);
        verify(cartJpaRepository, times(1)).existsByUser_Id(userId);
    }

    @Test
    public void testCreateCart() {
        Integer userId = 1;
        CartEntity cartEntity = CartEntity.builder()
                .user(UserEntity.builder().id(userId).build())
                .build();

        cartJpaAdapter.createCart(userId);

        verify(cartJpaRepository, times(1)).save(cartEntity);
    }

    @Test
    public void testGetCartByUserId() {
        Integer userId = 1;
        CartEntity cartEntity = CartEntity.builder().id(1).build();
        Cart cart = new Cart();
        when(cartJpaRepository.findByUser_Id(userId)).thenReturn(Optional.of(cartEntity));
        when(cartEntityMapper.entityToModel(cartEntity)).thenReturn(cart);

        Cart result = cartJpaAdapter.getCartByUserId(userId);

        assertNotNull(result);
        assertEquals(cart, result);
        verify(cartJpaRepository, times(1)).findByUser_Id(userId);
        verify(cartEntityMapper, times(1)).entityToModel(cartEntity);
    }

    @Test
    public void testGetCartByUserId_NotFound() {
        Integer userId = 1;
        when(cartJpaRepository.findByUser_Id(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartJpaAdapter.getCartByUserId(userId));
        verify(cartJpaRepository, times(1)).findByUser_Id(userId);
    }

    @Test
    public void testUpdateCoupon() {
        Integer userId = 1;
        Integer couponId = 1;
        CartEntity cartEntity = CartEntity.builder().id(1).build();
        when(cartJpaRepository.findByUser_Id(userId)).thenReturn(Optional.of(cartEntity));

        cartJpaAdapter.updateCoupon(userId, couponId);

        assertEquals(couponId, cartEntity.getCoupon().getId());
        verify(cartJpaRepository, times(1)).findByUser_Id(userId);
        verify(cartJpaRepository, times(1)).save(cartEntity);
    }

    @Test
    public void testUpdateCoupon_NotFound() {
        Integer userId = 1;
        Integer couponId = 1;
        when(cartJpaRepository.findByUser_Id(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartJpaAdapter.updateCoupon(userId, couponId));
        verify(cartJpaRepository, times(1)).findByUser_Id(userId);
    }
}