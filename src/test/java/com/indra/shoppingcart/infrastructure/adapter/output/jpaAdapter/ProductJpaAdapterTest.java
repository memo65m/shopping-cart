package com.indra.shoppingcart.infrastructure.adapter.output.jpaAdapter;

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
import com.indra.shoppingcart.domain.model.Product;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.ProductEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.mapper.ProductEntityMapper;
import com.indra.shoppingcart.infrastructure.adapter.output.repository.ProductJpaRepository;

@ExtendWith(MockitoExtension.class)
public class ProductJpaAdapterTest {

    @Mock
    private ProductJpaRepository productJpaRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductJpaAdapter productJpaAdapter;

    @Test
    public void testGetProductById_Success() {
        Integer productId = 1;
        ProductEntity productEntity = new ProductEntity();
        Product product = new Product();

        when(productJpaRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productEntityMapper.entityToModel(productEntity)).thenReturn(product);

        Product result = productJpaAdapter.getProductById(productId);

        assertEquals(product, result);
    }

    @Test
    public void testGetProductById_NotFound() {
        Integer productId = 1;

        when(productJpaRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productJpaAdapter.getProductById(productId));
    }
}
