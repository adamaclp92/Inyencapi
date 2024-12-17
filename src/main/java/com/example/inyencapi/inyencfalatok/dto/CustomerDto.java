package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


/**
 * Customer
 */

public class CustomerDto   {

  @JsonProperty("customer_name")
  @NotNull(message = "customer_name is required.")
  private String customerName;

  @JsonProperty("customer_phone_number")
  @NotNull(message = "customer_phone_number is required.")
  private String customerPhoneNumber;

  @JsonProperty("customer_email")
  @NotNull(message = "customer_email is required.")
  @Email(message = "Invalid email format.")
  private String customerEmail;

  public CustomerDto() {
  }

  public CustomerDto(String customerName, String customerPhoneNumber, String customerEmail) {
    this.customerName = customerName;
    this.customerPhoneNumber = customerPhoneNumber;
    this.customerEmail = customerEmail;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerPhoneNumber() {
    return customerPhoneNumber;
  }

  public void setCustomerPhoneNumber(String customerPhoneNumber) {
    this.customerPhoneNumber = customerPhoneNumber;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerDto customer = (CustomerDto) o;
    return Objects.equals(this.customerName, customer.customerName) &&
        Objects.equals(this.customerPhoneNumber, customer.customerPhoneNumber) &&
        Objects.equals(this.customerEmail, customer.customerEmail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerName, customerPhoneNumber, customerEmail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Customer {\n");
    
    sb.append("    customerName: ").append(toIndentedString(customerName)).append("\n");
    sb.append("    customerPhoneNumber: ").append(toIndentedString(customerPhoneNumber)).append("\n");
    sb.append("    customerEmail: ").append(toIndentedString(customerEmail)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
