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
    public Integer quantity;
    public Cart cart;
    public Product product;
}
