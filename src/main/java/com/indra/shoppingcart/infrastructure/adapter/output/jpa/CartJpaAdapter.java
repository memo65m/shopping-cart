package com.indra.shoppingcart.infrastructure.adapter.output.jpa;

import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CouponEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.UserEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartJpaAdapter implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final CartEntityMapper cartEntityMapper;
    
    public Boolean existsCartByUserId(Integer userId) {
        return cartJpaRepository.existsByUser_Id(userId);
    }

    public void createCart(Integer userId) {
        CartEntity cart = CartEntity.builder()
                .user(UserEntity.builder().id(userId).build())
                .build();
        cartJpaRepository.save(cart);
    }

    public Cart getCartByUserId(Integer userId) {
        CartEntity cart = cartJpaRepository.findByUser_Id(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        return cartEntityMapper.entityToModel(cart);
    }

    public void updateCoupon(Integer userId, Integer couponId) {
        CartEntity cartEntity = cartJpaRepository.findByUser_Id(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        cartEntity.setCoupon(CouponEntity.builder().id(couponId).build());
        cartJpaRepository.save(cartEntity);
    }

}
