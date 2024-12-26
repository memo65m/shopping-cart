package com.indra.shoppingcart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private Integer stock;
    private String name;
    private Double unitPrice;
    private Integer discountPercentage;
    private Double discountPrice;
}
