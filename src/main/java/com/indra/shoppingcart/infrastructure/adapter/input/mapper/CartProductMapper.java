package com.indra.shoppingcart.infrastructure.adapter.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.CartProductRequest;

@Mapper(componentModel = "spring")
public interface CartProductMapper {
    @Mapping(source = "userId", target = "cart.user.id")
    @Mapping(source = "productId", target = "product.id")
    CartProduct cartProductRequestToCartProduct(CartProductRequest cartProductRequest);
}