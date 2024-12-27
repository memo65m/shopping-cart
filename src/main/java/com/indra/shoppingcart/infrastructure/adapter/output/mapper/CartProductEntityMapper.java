package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartProductEntity;

@Mapper(componentModel = "spring", uses = ProductEntityMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartProductEntityMapper {

    @InheritInverseConfiguration
    CartProductEntity modelToEntity(CartProduct cartProduct);

    @Mapping(target = "cart", ignore = true)
    CartProduct entityToModel(CartProductEntity cartProductEntity);

    @BeforeMapping
    default void beforeEntityToModel(@MappingTarget CartProduct.CartProductBuilder cartProduct,
            CartProductEntity cartProductEntity) {
        if (cartProduct != null && cartProductEntity != null) {
            try {
                Double subTotal = cartProductEntity.getProduct().getUnitPrice() * cartProductEntity.getQuantity();
                subTotal = BigDecimal.valueOf(subTotal).setScale(3, RoundingMode.HALF_UP).doubleValue();
                cartProduct.subTotal(subTotal);
            } catch (Exception e) {
                cartProduct.subTotal(0.0);
            }
        }
    }

}
