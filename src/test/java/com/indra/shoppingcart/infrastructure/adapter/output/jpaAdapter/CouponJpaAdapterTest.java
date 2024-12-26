package com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.Coupon;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CouponEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CouponEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CouponJpaRepository;

@ExtendWith(MockitoExtension.class)
public class CouponJpaAdapterTest {

    @Mock
    private CouponJpaRepository couponJpaRepository;

    @Mock
    private CouponEntityMapper couponEntityMapper;

    @InjectMocks
    private CouponJpaAdapter couponJpaAdapter;

    @Test
    public void testGetCoupon_Success() {
        Integer userId = 1;
        String couponCode = "TESTCODE";
        CouponEntity couponEntity = new CouponEntity();
        Coupon coupon = new Coupon();

        when(couponJpaRepository.findByUser_IdAndCode(userId, couponCode)).thenReturn(Optional.of(couponEntity));
        when(couponEntityMapper.entityToModel(couponEntity)).thenReturn(coupon);

        Coupon result = couponJpaAdapter.getCoupon(userId, couponCode);

        assertEquals(coupon, result);
    }

    @Test
    public void testGetCoupon_NotFound() {
        Integer userId = 1;
        String couponCode = "INVALIDCODE";

        when(couponJpaRepository.findByUser_IdAndCode(userId, couponCode)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            couponJpaAdapter.getCoupon(userId, couponCode);
        });
    }
}
