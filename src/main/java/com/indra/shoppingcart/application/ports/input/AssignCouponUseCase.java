package com.indra.shoppingcart.application.ports.input;

public interface AssignCouponUseCase {
    void execute(Integer userId, String couponCode);
}
