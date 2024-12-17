package com.example.inyencapi.inyencfalatok.mapper;

import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetOrderMapper {

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "orderStatus", target = "orderStatus")
    OrderDto toOrderDto(Order order);



}
