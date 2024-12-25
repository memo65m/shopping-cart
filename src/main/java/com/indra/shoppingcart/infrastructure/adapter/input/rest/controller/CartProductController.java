package com.indra.shoppingcart.infrastructure.adapter.input.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indra.shoppingcart.application.ports.input.CreateCartUseCase;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.CartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ExceptionResponseDto;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ResponseDto;

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
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<String>> addProduct(@RequestBody @Valid CartProductRequest cartProductRequest) {

        createCartUseCase.execute(1);

        ResponseDto<String> responseDto = ResponseDto.<String>builder()
                .message("Operación exitosa")
                .value("Producto agregado")
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
