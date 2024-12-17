package com.example.inyencapi.inyencfalatok.entity;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Entity
@Table(name = "order_items")
@Getter
@Setter
@ToString
public class OrderItem  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false, columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
	private UUID id = UUID.randomUUID();
	
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
	
	@ManyToOne
	@JoinColumn(name = "meal_id" , nullable = false)
	private Meal meal;
	
	@Column(name="quantity", nullable = false)
	private int quantity;

}
