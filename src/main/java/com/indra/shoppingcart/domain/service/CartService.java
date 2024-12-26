package com.indra.shoppingcart.domain.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.application.ports.output.CouponRepository;
import com.indra.shoppingcart.domain.constant.Status;
import com.indra.shoppingcart.domain.exception.BadRequestException;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.domain.model.Coupon;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Cart getCartByUser(Integer userId) {
        Boolean exists = cartRepository.existsCartByUserId(userId);
        Cart cart = Cart.builder().build();
        if (Boolean.TRUE.equals(exists)) {
            cart = cartRepository.getCartByUserId(userId);
        }
        return cart;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createCart(Integer userId) {
        Boolean exists = cartRepository.existsCartByUserId(userId);
        if (Boolean.FALSE.equals(exists)) {
            cartRepository.createCart(userId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void assignCoupon(Integer userId, String couponCode) {
        Coupon coupon = couponRepository.getCoupon(userId, couponCode);
        Cart cart = cartRepository.getCartByUserId(userId);

        if (!Status.ACTIVE.equals(coupon.getStatus())) {
            throw new BadRequestException("Coupon is not active");
        }

        if (cart.getCoupon() != null && Boolean.TRUE.equals(coupon.getId().equals(cart.getCoupon().getId()))) {
            throw new BadRequestException("Coupon already assigned");
        }

        cartRepository.updateCoupon(userId, coupon.getId());

    }

}
