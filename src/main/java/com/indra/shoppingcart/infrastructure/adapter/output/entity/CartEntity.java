package com.indra.shoppingcart.infrastructure.adapter.output.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class CartEntity {
    @Id
    private Integer id;
}
