package com.indra.shoppingcart.application.ports.output;

import com.indra.shoppingcart.domain.model.Product;

public interface ProductRepository {
    Product getProductById(Integer productId);
}
