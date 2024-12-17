package com.example.inyencapi.inyencfalatok.dto;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * MealQuantity
 */
public class MealQuantityDto   {
	
  @JsonProperty("meal_id")// Exclude from JSON if absent   // FAIL setting if the value is null
  private UUID mealId = null;

  @JsonProperty("meal_quantity")
  private Integer mealQuantity = null;


  public MealQuantityDto() {
  }

  public MealQuantityDto(UUID mealId, Integer mealQuantity) {
    this.mealId = mealId;
    this.mealQuantity = mealQuantity;
  }

  public UUID getMealId() {
    return mealId;
  }

  public void setMealId(UUID mealId) {
    this.mealId = mealId;
  }

  public Integer getMealQuantity() {
    return mealQuantity;
  }

  public void setMealQuantity(Integer mealQuantity) {
    this.mealQuantity = mealQuantity;
  }

  /**
   * Get mealId
   * @return mealId
   **/
  


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MealQuantityDto mealQuantity = (MealQuantityDto) o;
    return Objects.equals(this.mealId, mealQuantity.mealId) &&
        Objects.equals(this.mealQuantity, mealQuantity.mealQuantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mealId, mealQuantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MealQuantity {\n");
    
    sb.append("    mealId: ").append(toIndentedString(mealId)).append("\n");
    sb.append("    mealQuantity: ").append(toIndentedString(mealQuantity)).append("\n");
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
