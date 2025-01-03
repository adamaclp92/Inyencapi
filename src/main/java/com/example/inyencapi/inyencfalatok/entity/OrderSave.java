package com.example.inyencapi.inyencfalatok.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="order_save")
public class OrderSave implements Serializable {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id = UUID.randomUUID();

    @Column(name = "order_id", nullable = false, columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID orderId = UUID.randomUUID();

    @Column(name = "order_request", nullable = false, columnDefinition = "TEXT")
    private String orderRequest;

    @JsonProperty("timestamp")
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public OrderSave(UUID orderId, String orderRequest) {
        this.orderId = orderId;
        this.orderRequest = orderRequest;
    }

    public OrderSave() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(String orderRequest) {
        this.orderRequest = orderRequest;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OrderSave{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderRequest='" + orderRequest + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
