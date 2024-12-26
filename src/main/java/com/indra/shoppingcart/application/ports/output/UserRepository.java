package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.User;

public interface UserRepository {
    User getUserById(Integer userId);
}
