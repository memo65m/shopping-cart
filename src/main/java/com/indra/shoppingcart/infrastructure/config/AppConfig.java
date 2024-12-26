package com.indra.shoppingcart.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.indra.shoppingcart.application.ports.input.CreateCartProductUseCase;
import com.indra.shoppingcart.application.ports.input.CreateCartUseCase;
import com.indra.shoppingcart.application.ports.input.DeleteCartProductUseCase;
import com.indra.shoppingcart.application.ports.input.GetCartUseCase;
import com.indra.shoppingcart.application.ports.input.UpdateCartProductUseCase;
import com.indra.shoppingcart.application.ports.output.CartProductRepository;
import com.indra.shoppingcart.application.ports.output.CartRepository;
import com.indra.shoppingcart.application.ports.output.ProductRepository;
import com.indra.shoppingcart.domain.service.CartProductService;
import com.indra.shoppingcart.domain.service.CartService;
import com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter.CartJpaAdapter;
import com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter.CartProductJpaAdapter;
import com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter.ProductJpaAdapter;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartProductEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.ProductEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartJpaRepository;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartProductJpaRepository;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.ProductJpaRepository;

@Configuration
public class AppConfig {

    @Bean
    CartService cartService(CartRepository cartRepository) {
        return new CartService(cartRepository);
    }

    @Bean
    CartProductService cartProductService(CartProductRepository cartProductRepository,
            ProductRepository productRepository, CartRepository cartRepository) {
        return new CartProductService(cartProductRepository, productRepository, cartRepository);
    }

    @Bean
    CartRepository getCartRepository(CartJpaRepository cartJpaRepository, CartEntityMapper cartEntityMapper) {
        return new CartJpaAdapter(cartJpaRepository, cartEntityMapper);
    }

    @Bean
    CartProductRepository getCartProductRepository(CartProductJpaRepository cartProductJpaRepository,
            CartProductEntityMapper cartProductEntityMapper) {
        return new CartProductJpaAdapter(cartProductJpaRepository, cartProductEntityMapper);
    }

    @Bean
    ProductRepository getProductRepository(ProductJpaRepository productJpaRepository,
            ProductEntityMapper productEntityMapper) {
        return new ProductJpaAdapter(productJpaRepository, productEntityMapper);
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
    CreateCartProductUseCase createCartProductUseCase(CartProductService cartProductService) {
        return cartProductService::createCartProduct;
    }

    @Bean
    DeleteCartProductUseCase deleteCartProductUseCase(CartProductService cartProductService) {
        return cartProductService::deleteCartProduct;
    }

    @Bean
    UpdateCartProductUseCase updateCartProductUseCase(CartProductService cartProductService) {
        return cartProductService::updateCartProduct;
    }

}
