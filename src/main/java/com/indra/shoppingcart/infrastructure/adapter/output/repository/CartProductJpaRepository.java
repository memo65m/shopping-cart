package com.indra.shoppingcart.infrastructure.adapter.output.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartProductEntity;

@Repository
public interface CartProductJpaRepository extends JpaRepository<CartProductEntity, Integer> {

    boolean existsByCart_IdAndProduct_Id(Integer cartId, Integer productId);

    Optional<CartProductEntity> findByCart_IdAndProduct_Id(Integer cartId, Integer productId);

}
