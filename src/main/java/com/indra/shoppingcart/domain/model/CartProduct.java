package com.indra.shoppingcart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    private Integer id;
    private Integer quantity;
    private Double subTotal;
    private Cart cart;
    private Product product;
}
