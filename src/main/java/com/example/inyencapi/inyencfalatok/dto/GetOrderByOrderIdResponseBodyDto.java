package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;



/**
 * GetOrderByOrderIdResponseBody
 */

public class GetOrderByOrderIdResponseBodyDto   {
	
  @JsonProperty("customer_datas")
  private CustomerDto customerDatas;

  @JsonProperty("customer_address")
  private AddressDto customerAddress;

  @JsonProperty("order_datas")
  private OrderDto orderDatas;

  @JsonProperty("meal_items")
  private List<MealQuantityDto> mealItems;

  public GetOrderByOrderIdResponseBodyDto() {
  }

  public GetOrderByOrderIdResponseBodyDto(CustomerDto customerDatas, AddressDto customerAddress, OrderDto orderDatas, List<MealQuantityDto> mealItems) {
    this.customerDatas = customerDatas;
    this.customerAddress = customerAddress;
    this.orderDatas = orderDatas;
    this.mealItems = mealItems;
  }

  public CustomerDto getCustomerDatas() {
    return customerDatas;
  }

  public void setCustomerDatas(CustomerDto customerDatas) {
    this.customerDatas = customerDatas;
  }

  public AddressDto getCustomerAddress() {
    return customerAddress;
  }

  public void setCustomerAddress(AddressDto customerAddress) {
    this.customerAddress = customerAddress;
  }

  public OrderDto getOrderDatas() {
    return orderDatas;
  }

  public void setOrderDatas(OrderDto orderDatas) {
    this.orderDatas = orderDatas;
  }

  public List<MealQuantityDto> getMealItems() {
    return mealItems;
  }

  public void setMealItems(List<MealQuantityDto> mealItems) {
    this.mealItems = mealItems;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetOrderByOrderIdResponseBodyDto getOrderByOrderIdResponseBody = (GetOrderByOrderIdResponseBodyDto) o;
    return Objects.equals(this.customerDatas, getOrderByOrderIdResponseBody.customerDatas) &&
            Objects.equals(this.customerAddress, getOrderByOrderIdResponseBody.customerAddress) &&
        Objects.equals(this.orderDatas, getOrderByOrderIdResponseBody.orderDatas) &&
        Objects.equals(this.mealItems, getOrderByOrderIdResponseBody.mealItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerDatas, orderDatas, mealItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetOrderByOrderIdResponseBody {\n");
    
    sb.append("    customerDatas: ").append(toIndentedString(customerDatas)).append("\n");
    sb.append("    customerAddress: ").append(toIndentedString(customerAddress)).append("\n");
    sb.append("    orderDatas: ").append(toIndentedString(orderDatas)).append("\n");
    sb.append("    mealItems: ").append(toIndentedString(mealItems)).append("\n");
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
