package com.indra.shoppingcart.infrastructure.adapter.input.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.shoppingcart.application.ports.input.AssignCouponUseCase;
import com.indra.shoppingcart.application.ports.input.GetCartUseCase;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.AssignCouponRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartResponse;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ExceptionResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.mapper.CartMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final GetCartUseCase getCartUseCase;
    private final AssignCouponUseCase assignCouponUseCase;
    private final CartMapper cartMapper;

    @ApiOperation(value = "Get Cart", notes = "Get Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<CartResponse>> getCartByUser(@RequestParam(value = "userId") Integer userId) {

        Cart cart = getCartUseCase.execute(userId);
        CartResponse cartResponse = cart != null ? cartMapper.cartToCartResponse(cart) : null;

        ResponseDto<CartResponse> responseDto = ResponseDto.<CartResponse>builder()
                .message("Operación exitosa")
                .value(cartResponse)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @ApiOperation(value = "Assign Coupon", notes = "Assign Coupon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping(value = "/assignCoupon", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<String>> assignCoupon(@RequestBody @Valid AssignCouponRequest request)  {

        assignCouponUseCase.execute(request.getUserId(), request.getCouponCode());

        ResponseDto<String> responseDto = ResponseDto.<String>builder()
                .message("Operación exitosa")
                .value("Cupón asignado correctamente")
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
