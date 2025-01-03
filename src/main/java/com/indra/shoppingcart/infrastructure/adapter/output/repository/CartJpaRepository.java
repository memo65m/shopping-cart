package com.indra.shoppingcart.infrastructure.adapter.output.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartEntity;

@Repository
public interface CartJpaRepository extends JpaRepository<CartEntity, Integer> {

    boolean existsByUser_Id(Integer userId);

    Optional<CartEntity> findByUser_Id(Integer userId);

}
