package com.indra.shoppingcart.infrastructure.adapter.input.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Double subTotal;
    private Double discount;
    private Double discountCoupon;
    private Double total;
    private List<CartProductResponse> cartProducts;
    private CouponResponse coupon;
}
