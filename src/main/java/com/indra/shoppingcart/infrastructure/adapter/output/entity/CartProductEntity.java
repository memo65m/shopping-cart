package com.indra.shoppingcart.infrastructure.adapter.output.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_cart_product")
public class CartProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer quantity;

	@ManyToOne
	@JoinColumn(name="cart")
	private CartEntity cart;

	@ManyToOne
	@JoinColumn(name="product")
	private ProductEntity product;

}