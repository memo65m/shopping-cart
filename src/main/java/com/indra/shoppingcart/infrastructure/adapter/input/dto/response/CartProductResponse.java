package com.indra.shoppingcart.infrastructure.adapter.input.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CartProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer quantity;
    private ProductResponse product;
    
}
