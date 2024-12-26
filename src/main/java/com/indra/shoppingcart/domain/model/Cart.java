package com.indra.shoppingcart.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Integer id;
    private User user;
    private List<CartProduct> cartProducts;
    private Double subTotal;
    private Double discount;
    private Double discountCoupon;
    private Coupon coupon;
    private Double total;
}
