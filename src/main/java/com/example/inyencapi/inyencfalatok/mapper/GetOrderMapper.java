package com.example.inyencapi.inyencfalatok.mapper;

import com.example.inyencapi.inyencfalatok.dto.AddressDto;
import com.example.inyencapi.inyencfalatok.dto.CustomerDto;
import com.example.inyencapi.inyencfalatok.dto.MealQuantityDto;
import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Order;
import com.example.inyencapi.inyencfalatok.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetOrderMapper {

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "orderStatus", target = "orderStatus")
    OrderDto toOrderDto(Order order);

    @Mapping(source = "meal.id", target = "mealId")
    @Mapping(source = "quantity", target = "mealQuantity")
    MealQuantityDto toMealQuantityDto(OrderItem orderItem);


    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "customerPhoneNumber", target = "customerPhoneNumber")
    @Mapping(source = "customerEmail", target = "customerEmail")
    CustomerDto toCustomerDto(Customer customer);

    @Mapping(source = "zipCode", target = "addressZipCode")
    @Mapping(source = "city", target = "addressCity")
    @Mapping(source = "streetNumber", target = "addressStreetNumber")
    AddressDto toAddressDto(Address address);
}
