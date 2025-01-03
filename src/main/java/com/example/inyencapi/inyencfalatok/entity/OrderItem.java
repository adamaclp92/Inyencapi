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


	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItem{" +
				"id=" + id +
				", order=" + order.getOrderId() +
				", meal=" + meal.getId() +
				", quantity=" + quantity +
				'}';
	}
}
