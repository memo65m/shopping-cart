package com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter;

import com.indra.shoppingcart.application.ports.output.CartProductRepository;
import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartProductEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.CartProductEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.CartProductJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartProductJpaAdapter implements CartProductRepository {

    private final CartProductJpaRepository cartProductJpaRepository;
    private final CartProductEntityMapper cartProductEntityMapper;

    public CartProduct createCartProduct(CartProduct cartProduct) {
        CartProductEntity cartProductEntity = cartProductEntityMapper.modelToEntity(cartProduct);
        cartProductEntity = cartProductJpaRepository.save(cartProductEntity);
        return cartProductEntityMapper.entityToModel(cartProductEntity);
    }

    public Boolean existsCartProduct(Integer cardId, Integer productId) {
        return cartProductJpaRepository.existsByCart_IdAndProduct_Id(cardId, productId);
    }

    public CartProduct updateQuantity(CartProduct cartProduct) {
        CartProductEntity cartProductEntity = cartProductJpaRepository
                .findByCart_IdAndProduct_Id(cartProduct.getCart().getId(), cartProduct.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("CartProduct not found"));

        cartProductEntity.setQuantity(cartProduct.getQuantity());
        cartProductEntity = cartProductJpaRepository.save(cartProductEntity);
        return cartProductEntityMapper.entityToModel(cartProductEntity);
    }

}
