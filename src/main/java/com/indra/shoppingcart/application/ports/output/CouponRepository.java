package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.Coupon;

public interface CouponRepository {
    Coupon getCoupon(Integer userId, String couponCode);
}
