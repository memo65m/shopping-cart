package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.model.User;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    User entityToModel(UserEntity userEntity);

}
