package com.example.inyencapi.inyencfalatok.mapper;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderResponseBodyDto;
import com.example.inyencapi.inyencfalatok.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;

@Mapper(componentModel = "spring")
public interface PostNewOrderMapper {

	@Mapping(source="customerDatas.customerName", target="customerName")
	@Mapping(source="customerDatas.customerPhoneNumber", target="customerPhoneNumber")
	@Mapping(source="customerDatas.customerEmail", target="customerEmail")
	Customer toCustomerEntity(PostNewOrderRequestBodyDto dto);
	
	@Mapping(source="customerAddress.addressZipCode", target="zipCode")
	@Mapping(source="customerAddress.addressCity", target="city")
	@Mapping(source="customerAddress.addressStreetNumber", target="streetNumber")
	Address toAddressEntity(PostNewOrderRequestBodyDto dto);

}
