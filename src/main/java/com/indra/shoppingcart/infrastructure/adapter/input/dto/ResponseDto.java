package com.indra.shoppingcart.infrastructure.adapter.input.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String message;
    private T value;
}
