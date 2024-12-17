package com.example.inyencapi.inyencfalatok.dto;

import java.sql.Timestamp;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import org.threeten.bp.OffsetDateTime;


/**
 * Order
 */


public class OrderDto   {
	
  @JsonProperty("order_id")
  private String orderId = null;

  @JsonProperty("order_date")
  private Timestamp orderDate = null;

  /**
   * Gets or Sets orderStatus
   */

  public enum OrderStatusEnum {
    Feldolgozas_alatt, Kiszallitas_alatt, Teljesitve
  }

  @JsonProperty("order_status")
  private OrderStatusEnum orderStatus = null;

  public OrderDto() {
  }

  public OrderDto(String orderId, Timestamp orderDate, OrderStatusEnum orderStatus) {
    this.orderId = orderId;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Timestamp getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Timestamp orderDate) {
    this.orderDate = orderDate;
  }

  public OrderStatusEnum getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatusEnum orderStatus) {
    this.orderStatus = orderStatus;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderDto order = (OrderDto) o;
    return Objects.equals(this.orderId, order.orderId) &&
        Objects.equals(this.orderDate, order.orderDate) &&
        Objects.equals(this.orderStatus, order.orderStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, orderDate, orderStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
    sb.append("    orderStatus: ").append(toIndentedString(orderStatus)).append("\n");
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
