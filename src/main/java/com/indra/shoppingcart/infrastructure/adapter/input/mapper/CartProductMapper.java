package com.indra.shoppingcart.infrastructure.adapter.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.request.CartProductRequest;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.CartProductResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartProductMapper {
    @Mapping(source = "userId", target = "cart.user.id")
    @Mapping(source = "productId", target = "product.id")
    CartProduct cartProductRequestToCartProduct(CartProductRequest cartProductRequest);

    CartProductResponse cartProductToCartProductResponse(CartProduct cartProduct);

}