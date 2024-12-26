package com.indra.shoppingcart.infrastructure.adapter.output.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.shoppingcart.infrastructure.adapter.output.entity.CouponEntity;

@Repository
public interface CouponJpaRepository extends JpaRepository<CouponEntity, Integer> {

    Optional<CouponEntity> findByUser_IdAndCode(Integer userId, String couponCode);

}
