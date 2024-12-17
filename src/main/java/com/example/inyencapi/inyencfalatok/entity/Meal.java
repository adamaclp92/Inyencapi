package com.example.inyencapi.inyencfalatok.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "meals")
public class Meal implements Serializable{

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id = UUID.randomUUID();

    @Column(name = "meal_name", unique = true, nullable = false)
    private String mealName;

    @Column(name = "meal_description", columnDefinition = "TEXT")
    private String mealDescription;

    @Column(name = "meal_price", nullable = false)
    private int mealPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_availability", nullable = false)
    private MealAvailability mealAvailability = MealAvailability.elerheto;
    
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public enum MealAvailability {
    	elerheto, kifogyott
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public int getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(int mealPrice) {
        this.mealPrice = mealPrice;
    }

    public MealAvailability getMealAvailability() {
        return mealAvailability;
    }

    public void setMealAvailability(MealAvailability mealAvailability) {
        this.mealAvailability = mealAvailability;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", mealName='" + mealName + '\'' +
                ", mealDescription='" + mealDescription + '\'' +
                ", mealPrice=" + mealPrice +
                ", mealAvailability=" + mealAvailability +
                ", orderItems=" + orderItems +
                '}';
    }
}
