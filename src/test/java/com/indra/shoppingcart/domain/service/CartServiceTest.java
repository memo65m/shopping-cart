package com.indra.shoppingcart.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.application.ports.output.CouponRepository;
import com.indra.shoppingcart.application.ports.output.UserRepository;
import com.indra.shoppingcart.domain.constant.Status;
import com.indra.shoppingcart.domain.exception.BadRequestException;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.domain.model.Coupon;
import com.indra.shoppingcart.domain.model.User;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void testGetCartByUser_CartExists() {
        Integer userId = 1;
        Cart cart = Cart.builder().build();
        when(cartRepository.existsCartByUserId(userId)).thenReturn(true);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        Cart result = cartService.getCartByUser(userId);

        assertNotNull(result);
        verify(cartRepository).existsCartByUserId(userId);
        verify(cartRepository).getCartByUserId(userId);
    }

    @Test
    void testGetCartByUser_CartDoesNotExist() {
        Integer userId = 1;
        when(cartRepository.existsCartByUserId(userId)).thenReturn(false);

        Cart result = cartService.getCartByUser(userId);

        assertNull(result);
        verify(cartRepository).existsCartByUserId(userId);
        verify(cartRepository, never()).getCartByUserId(userId);
    }

    @Test
    void testCreateCart_CartDoesNotExist() {
        Integer userId = 1;
        User user = User.builder().id(userId).build();
        when(cartRepository.existsCartByUserId(userId)).thenReturn(false);
        when(userRepository.getUserById(userId)).thenReturn(user);

        cartService.createCart(userId);

        verify(cartRepository).existsCartByUserId(userId);
        verify(cartRepository).createCart(userId);
    }

    @Test
    void testCreateCart_CartExists() {
        Integer userId = 1;
        User user = User.builder().id(userId).build();
        when(cartRepository.existsCartByUserId(userId)).thenReturn(true);
        when(userRepository.getUserById(userId)).thenReturn(user);

        cartService.createCart(userId);

        verify(cartRepository).existsCartByUserId(userId);
        verify(cartRepository, never()).createCart(userId);
    }

    @Test
    void testAssignCoupon_CouponIsActiveAndNotAssigned() {
        Integer userId = 1;
        String couponCode = "COUPON123";
        Coupon coupon = Coupon.builder()
                .id(1)
                .status(Status.ACTIVE)
                .build();
        Cart cart = Cart.builder().build();
        when(couponRepository.getCoupon(userId, couponCode)).thenReturn(coupon);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        cartService.assignCoupon(userId, couponCode);

        verify(couponRepository).getCoupon(userId, couponCode);
        verify(cartRepository).getCartByUserId(userId);
        verify(cartRepository).updateCoupon(userId, coupon.getId());
    }

    @Test
    void testAssignCoupon_CouponIsNotActive() {
        Integer userId = 1;
        String couponCode = "COUPON123";
        Coupon coupon = Coupon.builder()
                .id(1)
                .status(Status.INACTIVE)
                .build();
        when(couponRepository.getCoupon(userId, couponCode)).thenReturn(coupon);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cartService.assignCoupon(userId, couponCode);
        });

        assertEquals("Coupon is not active", exception.getMessage());
        verify(couponRepository).getCoupon(userId, couponCode);
        verify(cartRepository, never()).updateCoupon(userId, coupon.getId());
    }

    @Test
    void testAssignCoupon_CouponAlreadyAssigned() {
        Integer userId = 1;
        String couponCode = "COUPON123";

        Coupon coupon = Coupon.builder()
                .id(1)
                .status(Status.ACTIVE)
                .build();

        Cart cart = Cart.builder()
                .coupon(coupon)
                .build();

        when(couponRepository.getCoupon(userId, couponCode)).thenReturn(coupon);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cartService.assignCoupon(userId, couponCode);
        });

        assertEquals("Coupon already assigned", exception.getMessage());
        verify(couponRepository).getCoupon(userId, couponCode);
        verify(cartRepository).getCartByUserId(userId);
        verify(cartRepository, never()).updateCoupon(userId, coupon.getId());
    }
}
