package com.indra.shoppingcart.infrastructure.adapter.input.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indra.shoppingcart.application.ports.input.CreateCartProductUseCase;
import com.indra.shoppingcart.application.ports.input.CreateCartUseCase;
import com.indra.shoppingcart.application.ports.input.DeleteCartProductUseCase;
import com.indra.shoppingcart.application.ports.input.UpdateCartProductUseCase;
import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.CartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.DeleteCartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.UpdateCartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartProductResponse;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ExceptionResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.mapper.CartProductMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/cartProduct")
@RequiredArgsConstructor
@Validated
public class CartProductController {

    private final CreateCartUseCase createCartUseCase;
    private final CreateCartProductUseCase createCartProductUseCase;
    private final DeleteCartProductUseCase deleteCartProductUseCase;
    private final UpdateCartProductUseCase updateCartProductUseCase;
    private final CartProductMapper cartProductMapper;

    private static final String SUCCESS_MESSAGE = "Operación exitosa";

    @ApiOperation(value = "Add Product", notes = "Add product to shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<CartProductResponse>> addProduct(@RequestBody @Valid CartProductRequest cartProductRequest) {

        CartProduct cartProduct = cartProductMapper.cartProductRequestToCartProduct(cartProductRequest);
        createCartUseCase.execute(cartProductRequest.getUserId());
        cartProduct = createCartProductUseCase.execute(cartProduct);

        ResponseDto<CartProductResponse> responseDto = ResponseDto.<CartProductResponse>builder()
                .message(SUCCESS_MESSAGE)
                .value(cartProductMapper.cartProductToCartProductResponse(cartProduct))
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @ApiOperation(value = "Delete Product", notes = "Delete product from shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<String>> deleteProduct(@RequestBody @Valid DeleteCartProductRequest request) {


        deleteCartProductUseCase.execute(request.getCartProductId(), request.getUserId());

        ResponseDto<String> responseDto = ResponseDto.<String>builder()
                .message(SUCCESS_MESSAGE)
                .value("Producto eliminado")
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @ApiOperation(value = "Update Product", notes = "Update product from shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "501", description = "Not implemented", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<String>> updateProduct(@RequestBody @Valid UpdateCartProductRequest request) {

        CartProduct cartProduct = cartProductMapper.updateCartProductRequestToCartProduct(request);
        String result = updateCartProductUseCase.execute(cartProduct);

        ResponseDto<String> responseDto = ResponseDto.<String>builder()
                .message(SUCCESS_MESSAGE)
                .value(result)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
