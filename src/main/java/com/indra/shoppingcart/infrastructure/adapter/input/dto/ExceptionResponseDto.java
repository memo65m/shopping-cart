package com.indra.shoppingcart.infrastructure.adapter.input.dto;

public class ExceptionResponseDto extends ResponseDto<String> {

    public ExceptionResponseDto(String message, String value) {
        super(message, value);
    }
    
}
