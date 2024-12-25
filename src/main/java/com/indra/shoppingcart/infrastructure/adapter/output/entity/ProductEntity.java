package com.indra.shoppingcart.infrastructure.adapter.output.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private Integer stock;

	@Column(name="unit_price")
	private double unitPrice;

	@OneToMany(mappedBy="product")
	private List<CartProductEntity> cartProducts;

	@OneToMany(mappedBy="product")
	private List<DiscountEntity> discounts;

}