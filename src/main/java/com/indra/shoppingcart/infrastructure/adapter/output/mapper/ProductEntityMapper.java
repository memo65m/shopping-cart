package com.indra.shoppingcart.infrastructure.adapter.output.mapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.indra.shoppingcart.domain.model.Product;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.DiscountEntity;
import com.indra.shoppingcart.infrastructure.adapter.output.entity.ProductEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {
    Product entityToModel(ProductEntity productEntity);

    @BeforeMapping
    default void beforeEntityToModel(@MappingTarget Product.ProductBuilder product, ProductEntity productEntity) {
        if (product != null && productEntity != null) {
            AtomicInteger discountPercentage = new AtomicInteger(0);
            LocalDate currentDate = LocalDate.now();
            Integer currentMont = currentDate.getMonthValue();
            Optional.ofNullable(productEntity.getDiscounts()).ifPresent(l -> {
                for (DiscountEntity discount : l) {
                    if (currentMont >= discount.getStartMonth() && currentMont <= discount.getExpirationMonth()) {
                        discountPercentage.addAndGet(discount.getDiscountPercentage());
                    }
                }
            });
            product.discountPercentage(discountPercentage.get());
            product.discountPrice(0.0);
            
            if (discountPercentage.get() > 0) {
                product.discountPrice(productEntity.getUnitPrice() - (productEntity.getUnitPrice() * (discountPercentage.get() / 100.0)));
            }

        }
    }
}
