package com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter;

import com.indra.shoppingcart.application.ports.output.CouponRepository;
import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.Coupon;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CouponEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CouponEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CouponJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponJpaAdapter implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponEntityMapper couponEntityMapper;

    public Coupon getCoupon(Integer userId, String couponCode) {
        CouponEntity couponEntity = couponJpaRepository.findByUser_IdAndCode(userId, couponCode)
                .orElseThrow(() -> new NotFoundException("Coupon not found"));
        return couponEntityMapper.entityToModel(couponEntity);
    }
    
}
