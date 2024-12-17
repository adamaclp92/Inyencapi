package com.example.inyencapi.inyencfalatok.dto;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.validation.constraints.NotNull;

/**
 * Address
 */
public class AddressDto {

	 @JsonProperty("address_zip_code")
	 @NotNull(message = "address_zip_code is required.")
	  private Integer addressZipCode = null;

	  @JsonProperty("address_city")
	  @NotNull(message = "address_city is required.")
	  private String addressCity = null;

	  @JsonProperty("address_street_number")
	  @NotNull(message = "address_street_number is required.")
	  private String addressStreetNumber = null;

	public AddressDto() {
	}

	public AddressDto(Integer addressZipCode, String addressCity, String addressStreetNumber) {
		this.addressZipCode = addressZipCode;
		this.addressCity = addressCity;
		this.addressStreetNumber = addressStreetNumber;
	}

	public Integer getAddressZipCode() {
		return addressZipCode;
	}

	public void setAddressZipCode(Integer addressZipCode) {
		this.addressZipCode = addressZipCode;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressStreetNumber() {
		return addressStreetNumber;
	}

	public void setAddressStreetNumber(String addressStreetNumber) {
		this.addressStreetNumber = addressStreetNumber;
	}

	@Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    AddressDto address = (AddressDto) o;
	    return Objects.equals(this.addressZipCode, address.addressZipCode) &&
	        Objects.equals(this.addressCity, address.addressCity) &&
	        Objects.equals(this.addressStreetNumber, address.addressStreetNumber);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(addressZipCode, addressCity, addressStreetNumber);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Address {\n");
	    
	    sb.append("    addressZipCode: ").append(toIndentedString(addressZipCode)).append("\n");
	    sb.append("    addressCity: ").append(toIndentedString(addressCity)).append("\n");
	    sb.append("    addressStreetNumber: ").append(toIndentedString(addressStreetNumber)).append("\n");
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