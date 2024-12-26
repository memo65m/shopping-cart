package com.indra.shoppingcart.infrastructure.adapter.input.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.shoppingcart.application.ports.input.GetCartUseCase;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartResponse;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ExceptionResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.mapper.CartMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final GetCartUseCase getCartUseCase;
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

        ResponseDto<CartResponse> responseDto = ResponseDto.<CartResponse>builder()
                .message("Operación exitosa")
                .value(cartMapper.cartToCartResponse(cart))
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
