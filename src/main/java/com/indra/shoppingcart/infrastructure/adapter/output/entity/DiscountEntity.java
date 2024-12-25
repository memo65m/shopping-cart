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
@Table(name="tb_discount")
public class DiscountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="discount_percentage")
	private Integer discountPercentage;

	@Column(name="expiration_month")
	private Integer expirationMonth;

	@Column(name="start_month")
	private Integer startMonth;

	@ManyToOne
	@JoinColumn(name="product")
	private ProductEntity product;

}