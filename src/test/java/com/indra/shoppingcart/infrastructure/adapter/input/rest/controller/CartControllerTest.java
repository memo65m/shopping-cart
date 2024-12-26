package com.indra.shoppingcart.infrastructure.adapter.input.rest.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indra.shoppingcart.application.ports.input.AssignCouponUseCase;
import com.indra.shoppingcart.application.ports.input.GetCartUseCase;
import com.indra.shoppingcart.domain.model.Cart;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.AssignCouponRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartResponse;
import com.indra.shoppingcart.infrastructure.adapter.input.mapper.CartMapper;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCartUseCase getCartUseCase;

    @MockBean
    private AssignCouponUseCase assignCouponUseCase;

    @MockBean
    private CartMapper cartMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetCartByUser() throws Exception {
        Cart cart = new Cart();
        CartResponse cartResponse = new CartResponse();
        when(getCartUseCase.execute(anyInt())).thenReturn(cart);
        when(cartMapper.cartToCartResponse(cart)).thenReturn(cartResponse);

        mockMvc.perform(get("/cart/get")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAssignCoupon() throws Exception {
        AssignCouponRequest request = new AssignCouponRequest();
        request.setUserId(1);
        request.setCouponCode("COUPON123");

        mockMvc.perform(put("/cart/assignCoupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
