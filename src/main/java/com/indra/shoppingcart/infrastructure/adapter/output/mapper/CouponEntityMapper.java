package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.constant.Status;
import com.indra.shoppingcart.domain.model.Coupon;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CouponEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponEntityMapper {

    Coupon entityToModel(CouponEntity couponEntity);

    @BeforeMapping
    default void beforeEntityToModel(@MappingTarget Coupon.CouponBuilder coupon, CouponEntity couponEntity) {
        if ( coupon != null && couponEntity != null ) {
            
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis >= couponEntity.getStartDate().getTime() && currentTimeMillis <= couponEntity.getExpirationDate().getTime()) {
                coupon.status(Status.ACTIVE);
            } else {
                coupon.status(Status.INACTIVE);
            }


        }
    }

}
