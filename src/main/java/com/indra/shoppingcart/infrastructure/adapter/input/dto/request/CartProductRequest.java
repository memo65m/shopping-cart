package com.indra.shoppingcart.infrastructure.adapter.input.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "El campo 'userId' es requerido.")
    private Integer userId;

    @NotNull(message = "El campo 'productId' es requerido.")
    private Integer productId;

    @NotNull(message = "El campo 'quantity' es requerido.")
    @Min(value = 1, message = "La cantidad mínima es 1.")
    private Integer quantity;
    
}
