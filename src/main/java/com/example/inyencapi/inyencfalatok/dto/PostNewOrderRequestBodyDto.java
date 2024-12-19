package com.example.inyencapi.inyencfalatok.dto;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


import jakarta.validation.constraints.NotNull;

/**
 * PostNewOrderRequestBody
 */

public class PostNewOrderRequestBodyDto   {
	
  private UUID orderId = UUID.randomUUID();

  @JsonProperty("customer_datas")
  @NotNull(message = "customer_datas property is missing!")
  private CustomerDto customerDatas;

  @JsonProperty("customer_address")
  @NotNull(message = "customer_address property is missing!")
  private AddressDto customerAddress;

  @JsonProperty("meal_items")
  @NotNull(message = "meal_items property is missing!")
  private List<MealQuantityDto> mealItems = new ArrayList<>();

  public PostNewOrderRequestBodyDto() {
  }

  public PostNewOrderRequestBodyDto(UUID orderId, CustomerDto customerDatas, AddressDto customerAddress, List<MealQuantityDto> mealItems) {
    this.orderId = orderId;
    this.customerDatas = customerDatas;
    this.customerAddress = customerAddress;
    this.mealItems = mealItems;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public void setOrderId(UUID orderId) {
    this.orderId = orderId;
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
    PostNewOrderRequestBodyDto postNewOrderRequestBody = (PostNewOrderRequestBodyDto) o;
    return Objects.equals(this.customerDatas, postNewOrderRequestBody.customerDatas) &&
            Objects.equals(this.customerAddress, postNewOrderRequestBody.customerAddress) &&
        Objects.equals(this.mealItems, postNewOrderRequestBody.mealItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerDatas, mealItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostNewOrderRequestBody {\n");
    
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    customerDatas: ").append(toIndentedString(customerDatas)).append("\n");
    sb.append("    customerAddress: ").append(toIndentedString(customerAddress)).append("\n");
    sb.append("    mealItems: ").append(toIndentedString(mealItems)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public void addMealItemsItem(MealQuantityDto meal1) {
    mealItems.add(meal1);
  }
}
