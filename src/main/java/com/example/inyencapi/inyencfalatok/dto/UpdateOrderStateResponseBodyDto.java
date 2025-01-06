package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * UpdateOrderStateResponseBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-01-06T07:40:46.790740759Z[GMT]")


public class UpdateOrderStateResponseBodyDto   {
    @JsonProperty("message")
    private String message = null;

    @JsonProperty("order_id")
    private String orderId = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public UpdateOrderStateResponseBodyDto() {
    }

    public UpdateOrderStateResponseBodyDto(String message, String orderId) {
        this.message = message;
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
        UpdateOrderStateResponseBodyDto updateOrderStateResponseBody = (UpdateOrderStateResponseBodyDto) o;
        return Objects.equals(this.message, updateOrderStateResponseBody.message) &&
                Objects.equals(this.orderId, updateOrderStateResponseBody.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, orderId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateOrderStateResponseBody {\n");

        sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
