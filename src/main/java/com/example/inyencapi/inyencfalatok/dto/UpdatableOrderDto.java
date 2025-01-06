package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;

import com.example.inyencapi.inyencfalatok.enums.OrderStatusNew;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class UpdatableOrderDto   {
    @JsonProperty("order_id")
    private UUID orderId = null;

    @JsonProperty("order_status_new")
    private OrderStatusNew orderStatusNew = null;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderStatusNew getOrderStatusNew() {
        return orderStatusNew;
    }

    public void setOrderStatusNew(OrderStatusNew orderStatusNew) {
        this.orderStatusNew = orderStatusNew;
    }

    public UpdatableOrderDto() {
    }

    public UpdatableOrderDto(UUID orderId, OrderStatusNew orderStatusNew) {
        this.orderId = orderId;
        this.orderStatusNew = orderStatusNew;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdatableOrderDto updatableOrder = (UpdatableOrderDto) o;
        return Objects.equals(this.orderId, updatableOrder.orderId) &&
                Objects.equals(this.orderStatusNew, updatableOrder.orderStatusNew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderStatusNew);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdatableOrder {\n");

        sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
        sb.append("    orderStatusNew: ").append(toIndentedString(orderStatusNew)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
