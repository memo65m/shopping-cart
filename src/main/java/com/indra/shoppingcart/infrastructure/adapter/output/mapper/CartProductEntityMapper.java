package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.model.CartProduct;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.CartProductEntity;

@Mapper(componentModel = "spring", uses = ProductEntityMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartProductEntityMapper {

    @InheritInverseConfiguration
    CartProductEntity modelToEntity(CartProduct cartProduct);

    @Mapping(target = "cart", ignore = true)
    CartProduct entityToModel(CartProductEntity cartProductEntity);


}
