package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.constant.Status;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.domain.model.Coupon;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartEntity;

@Mapper(componentModel = "spring", uses = { CartProductEntityMapper.class,
        CouponEntityMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartEntityMapper {
    Cart entityToModel(CartEntity cartEntity);

    @InheritInverseConfiguration
    CartEntity toEntity(Cart cart);

    @BeforeMapping
    default void beforeEntityToModel(@MappingTarget Cart.CartBuilder cart, CartEntity cartEntity) {

        if (cartEntity != null && cartEntity != null) {
            Double subTotal = cartEntity.getCartProducts().stream()
                    .mapToDouble(cartProductEntity -> cartProductEntity.getProduct().getUnitPrice() *
                            cartProductEntity.getQuantity())
                    .sum();

            subTotal = new BigDecimal(subTotal).setScale(3, RoundingMode.HALF_UP).doubleValue();

            cart.subTotal(subTotal);
        }
    }

    @AfterMapping
    default void afterEntityToModel(@MappingTarget Cart.CartBuilder cart, CartEntity cartEntity) {

        if (cartEntity != null && cartEntity != null) {

            Cart cartTmp = cart.build();

            Double discount = cart.build().getCartProducts().stream()
                    .mapToDouble(cartProduct -> cartProduct.getProduct().getDiscountPrice() *
                            cartProduct.getQuantity())
                    .sum();
            cart.discount(discount);

            Double discountCoupon = 0.0;
            if (cartEntity.getCoupon() != null) {
                discountCoupon = cartTmp.getSubTotal() * (cartTmp.getCoupon().getDiscountPercentage() / 100.0);
            }

            Coupon coupon = cartTmp.getCoupon();
            Double totalTmp = cartTmp.getSubTotal() - (discount + discountCoupon);
            boolean isCouponInactive = coupon == null || Status.INACTIVE.equals(coupon.getStatus());
            boolean isCouponActive = coupon != null && Status.ACTIVE.equals(coupon.getStatus());

            if (isCouponInactive) {
                cart.total(cartTmp.getSubTotal() - discount);
                cart.discountCoupon(0.0);
            } else if (isCouponActive && totalTmp < 0) {
                cart.total(cartTmp.getSubTotal() - discount);
                cart.discountCoupon(0.0);
                coupon.setStatus(Status.INVALID_FOR_NEGATIVE_BALANCE);
                cart.coupon(coupon);
            } else {
                cart.total(totalTmp);
                cart.discountCoupon(discountCoupon);
            }

            Double total = new BigDecimal(cart.build().getTotal()).setScale(3, RoundingMode.HALF_UP).doubleValue();
            cart.total(total);
        }
    }

}
