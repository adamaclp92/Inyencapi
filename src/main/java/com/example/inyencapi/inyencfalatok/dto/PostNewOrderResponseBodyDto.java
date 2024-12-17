package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PostNewOrderResponseBody
 */

public class PostNewOrderResponseBodyDto   {
	
  @JsonProperty("status")
  private String status;

  @JsonProperty("order_id")
  private UUID orderId;

  public PostNewOrderResponseBodyDto() {
  }

  public PostNewOrderResponseBodyDto(String status, UUID orderId) {
    this.status = status;
    this.orderId = orderId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public void setOrderId(UUID orderId) {
    this.orderId = orderId;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostNewOrderResponseBodyDto postNewOrderResponseBody = (PostNewOrderResponseBodyDto) o;
    return Objects.equals(this.status, postNewOrderResponseBody.status) &&
        Objects.equals(this.orderId, postNewOrderResponseBody.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, orderId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostNewOrderResponseBody {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
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
