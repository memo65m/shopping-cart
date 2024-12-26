package com.indra.shoppingcart.infrastructure.adapter.input.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AssignCouponRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "El campo 'userId' es requerido.")
    private Integer userId;

    @NotNull(message = "El campo 'couponCode' es requerido.")
    @NotBlank(message = "El campo 'couponCode' no puede estar vacío.")
    private String couponCode;

}
