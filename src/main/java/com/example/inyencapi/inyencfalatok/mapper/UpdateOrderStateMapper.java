package com.example.inyencapi.inyencfalatok.mapper;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateOrderStateMapper {

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderStatusNew", target = "orderStatus")
    Order toOrder(UpdatableOrderDto updatableOrderDto);

}
