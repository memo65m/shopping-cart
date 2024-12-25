package com.indra.shoppingcart.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.indra.shoppingcart.application.ports.input.CreateCartUseCase;
import com.indra.shoppingcart.application.ports.input.GetCartUseCase;
import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.domain.service.CartService;
import com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter.CartJpaAdapter;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartJpaRepository;

@Configuration
public class AppConfig {
    
    @Bean
    CartService cartService(CartRepository cartRepository) {
        return new CartService(cartRepository);
    }

    @Bean
    CartRepository getCartRepository(CartJpaRepository cartJpaRepository, CartEntityMapper cartEntityMapper) {
        return new CartJpaAdapter(cartJpaRepository, cartEntityMapper);
    }

    @Bean
    CartEntityMapper cartEntityMapper() {
        return new CartEntityMapper(modelMapper());
    }

    @Bean
    GetCartUseCase getCartUseCase(CartService cartService) {
        return cartService::getCart;
    }

    @Bean
    CreateCartUseCase createCartUseCase(CartService cartService) {
        return cartService::createCart;
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

}
