package com.indra.shoppingcart.infrastructure.adapter.output.jpa;

import com.indra.shoppingcart.application.ports.output.UserRepository;
import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.User;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.UserEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.UserEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    public User getUserById(Integer userId) {
        UserEntity userEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userEntityMapper.entityToModel(userEntity);

    }

}
