package com.indra.shoppingcart.infrastructure.adapter.output.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	@Column(name="discount_percentage")
	private Integer discountPercentage;

	@Temporal(TemporalType.DATE)
	@Column(name="expiration_date")
	private Date expirationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	@OneToMany(mappedBy="coupon")
	private List<CartEntity> carts;

	@ManyToOne
	@JoinColumn(name="beneficiary")
	private UserEntity user;

}