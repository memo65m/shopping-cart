package com.indra.shoppingcart.infrastructure.adapter.input.dto;

import java.io.Serializable;

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
    
}
