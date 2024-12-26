package com.indra.shoppingcart.infrastructure.adapter.input.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indra.shoppingcart.application.ports.input.CreateCartProductUseCase;
import com.indra.shoppingcart.application.ports.input.CreateCartUseCase;
import com.indra.shoppingcart.application.ports.input.DeleteCartProductUseCase;
import com.indra.shoppingcart.application.ports.input.UpdateCartProductUseCase;
import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.CartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.DeleteCartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.UpdateCartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartProductResponse;
import com.indra.shoppingcart.infrastructure.adapter.input.mapper.CartProductMapper;

@WebMvcTest(CartProductController.class)
class CartProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCartUseCase createCartUseCase;

    @MockBean
    private CreateCartProductUseCase createCartProductUseCase;

    @MockBean
    private DeleteCartProductUseCase deleteCartProductUseCase;

    @MockBean
    private UpdateCartProductUseCase updateCartProductUseCase;

    @MockBean
    private CartProductMapper cartProductMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddProduct() throws Exception {
        CartProductRequest request = CartProductRequest.builder()
                .userId(1)
                .productId(1)
                .quantity(1)
                .build();

        CartProduct cartProduct = new CartProduct();
        CartProductResponse response = new CartProductResponse();

        when(cartProductMapper.cartProductRequestToCartProduct(any(CartProductRequest.class))).thenReturn(cartProduct);
        when(createCartProductUseCase.execute(any(CartProduct.class))).thenReturn(cartProduct);
        when(cartProductMapper.cartProductToCartProductResponse(any(CartProduct.class))).thenReturn(response);

        mockMvc.perform(post("/cartProduct/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProduct() throws Exception {
        DeleteCartProductRequest request = DeleteCartProductRequest.builder()
                .cartProductId(1)
                .userId(1)
                .build();

        mockMvc.perform(delete("/cartProduct/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProduct() throws Exception {
        UpdateCartProductRequest request = UpdateCartProductRequest.builder()
                .cartProductId(1)
                .userId(1)
                .quantity(2)
                .build();

        CartProduct cartProduct = new CartProduct();

        when(cartProductMapper.updateCartProductRequestToCartProduct(any(UpdateCartProductRequest.class)))
                .thenReturn(cartProduct);
        when(updateCartProductUseCase.execute(any(CartProduct.class))).thenReturn("Producto actualizado");

        mockMvc.perform(put("/cartProduct/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}