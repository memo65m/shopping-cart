package com.indra.shoppingcart.domain.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private Integer id;
    private String code;
    private Integer discountPercentage;
    private Date startDate;
    private Date expirationDate;
    private String status;
}
