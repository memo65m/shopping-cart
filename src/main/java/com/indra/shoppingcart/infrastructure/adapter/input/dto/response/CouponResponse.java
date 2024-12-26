package com.indra.shoppingcart.infrastructure.adapter.input.dto.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CouponResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private Integer discountPercentage;
    private Date startDate;
    private Date expirationDate;
    private String status;

}
