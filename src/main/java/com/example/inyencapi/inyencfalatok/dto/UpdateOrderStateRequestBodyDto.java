package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;

import com.example.inyencapi.inyencfalatok.dto.UpdatableOrderDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;


/**
 * UpdateOrderStateRequestBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-01-06T07:40:46.790740759Z[GMT]")


public class UpdateOrderStateRequestBodyDto   {
    @JsonProperty("order_datas")
    private UpdatableOrderDto orderDatas = null;

    public UpdatableOrderDto getOrderDatas() {
        return orderDatas;
    }

    public void setOrderDatas(UpdatableOrderDto orderDatas) {
        this.orderDatas = orderDatas;
    }

    public UpdateOrderStateRequestBodyDto() {
    }

    public UpdateOrderStateRequestBodyDto(UpdatableOrderDto orderDatas) {
        this.orderDatas = orderDatas;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateOrderStateRequestBodyDto updateOrderStateRequestBody = (UpdateOrderStateRequestBodyDto) o;
        return Objects.equals(this.orderDatas, updateOrderStateRequestBody.orderDatas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDatas);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateOrderStateRequestBody {\n");

        sb.append("    orderDatas: ").append(toIndentedString(orderDatas)).append("\n");
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
