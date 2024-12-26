package com.indra.shoppingcart.infrastructure.adapter.output.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.domain.model.User;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.UserEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.UserEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @Test
    void testGetUserById_UserExists() {
        Integer userId = 1;
        UserEntity userEntity = new UserEntity();
        User user = new User();

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.entityToModel(userEntity)).thenReturn(user);

        User result = userJpaAdapter.getUserById(userId);

        assertEquals(user, result);
    }

    @Test
    void testGetUserById_UserNotFound() {
        Integer userId = 1;

        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userJpaAdapter.getUserById(userId));
    }
}
