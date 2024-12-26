package com.indra.shoppingcart.infrastructure.adapter.output.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.shoppingcart.infrastructure.adapter.output.entity.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
}
