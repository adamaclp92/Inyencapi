package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.AddressDto;
import com.example.inyencapi.inyencfalatok.dto.CustomerDto;
import com.example.inyencapi.inyencfalatok.dto.MealQuantityDto;
import com.example.inyencapi.inyencfalatok.dto.OrderDto;
import com.example.inyencapi.inyencfalatok.entity.Address;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Order;
import com.example.inyencapi.inyencfalatok.entity.OrderItem;
import com.example.inyencapi.inyencfalatok.enums.OrderStatus;
import com.example.inyencapi.inyencfalatok.mapper.GetOrderMapper;
import com.example.inyencapi.inyencfalatok.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetOrderByOrderIdServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private GetOrderMapper getOrderMapper;

    @Mock
    AddressesRepository addressesRepository;

    @Mock
    CustomersRepository customerRepository;

    @Mock
    MealsRepository mealsRepository;

    @Mock
    Order_ItemsRepository orderItemsRepository;


    @InjectMocks
    private GetOrderByOrderIdServiceImpl getOrderByOrderIdService;

    private UUID orderId;
    private UUID customerId;
    private UUID addressId;
    private Timestamp timestamp;
    private Order mockOrder;
    private Customer mockCustomer;
    private CustomerDto mockCustomerDto;
    private Address mockAddress;
    private AddressDto mockAddressDto;
    private OrderDto mockOrderDto;
    private List<OrderItem> mockOrderItems;
    private OrderItem mockOrderItem;
    private MealQuantityDto mockMealQuantityDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        addressId = UUID.randomUUID();
        timestamp = new Timestamp(System.currentTimeMillis());

        mockOrder = new Order();
        mockCustomer = new Customer();
        mockAddress = new Address();

        mockOrderDto = new OrderDto();
        mockAddressDto = new AddressDto();
        mockCustomerDto = new CustomerDto();

        mockOrderItems = new ArrayList<>();
        mockOrderItem = new OrderItem();
        mockMealQuantityDto = new MealQuantityDto();

    }

    @Test
    void getOrderFromRepository() {
        when(ordersRepository.findById(orderId)).thenReturn(Optional.ofNullable(mockOrder));

        Order result = getOrderByOrderIdService.GetOrderFromRepository(orderId);

        assertNotNull(result);
        assertEquals(mockOrder, result);
        verify(ordersRepository, times(1)).findById(orderId);
    }

    @Test
    void mapOrderToOrderDto() {

        mockOrder.setOrderId(orderId);
        mockOrder.setOrderDate(timestamp);

        mockOrderDto.setOrderId(orderId.toString());
        mockOrderDto.setOrderDate(timestamp);

        when(getOrderMapper.toOrderDto(mockOrder)).thenReturn(mockOrderDto);

        OrderDto result = getOrderByOrderIdService.MapOrderToOrderDto(mockOrder);

        assertNotNull(result);
        assertEquals(mockOrderDto.getOrderId(), result.getOrderId());
        assertEquals(mockOrderDto.getOrderDate(), result.getOrderDate());

        verify(getOrderMapper, times(1)).toOrderDto(mockOrder);
    }

    @Test
    void getMealsFromOrderItemList() {
        mockOrder.setOrderId(orderId);

        mockOrderItem.setOrder(mockOrder);

        mockOrderItems = List.of(mockOrderItem);

        when(orderItemsRepository.findAll()).thenReturn(mockOrderItems);
        when(getOrderMapper.toMealQuantityDto(mockOrderItem)).thenReturn(mockMealQuantityDto);

        List<MealQuantityDto> result = getOrderByOrderIdService.GetMealsFromOrderItemList(orderId);

        assertEquals(1, result.size());
        assertEquals(mockMealQuantityDto, result.get(0));

        verify(orderItemsRepository, times(1)).findAll();
        verify(getOrderMapper, times(1)).toMealQuantityDto(mockOrderItem);
    }

    @Test
    void getCustomerFromRepository() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(mockCustomer));

        Customer result = getOrderByOrderIdService.GetCustomerFromRepository(customerId);

        assertNotNull(result);
        assertEquals(mockCustomer, result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void mapCustomerToCustomerDto() {
       mockCustomer.setCustomerName("John Doe");
        mockCustomer.setCustomerEmail("test@test.com");

        mockCustomerDto.setCustomerName("John Doe");
        mockCustomerDto.setCustomerEmail("test@test.com");

        when(getOrderMapper.toCustomerDto(mockCustomer)).thenReturn(mockCustomerDto);

        CustomerDto result = getOrderByOrderIdService.MapCustomerToCustomerDto(mockCustomer);

        assertNotNull(result);
        assertEquals(mockCustomer.getCustomerName(), result.getCustomerName());
        assertEquals(mockCustomer.getCustomerEmail(), result.getCustomerEmail());

        verify(getOrderMapper, times(1)).toCustomerDto(mockCustomer);
    }

    @Test
    void getAddressFromRepository() {
        when(addressesRepository.findById(addressId)).thenReturn(Optional.ofNullable(mockAddress));
        when(getOrderMapper.toAddressDto(mockAddress)).thenReturn(mockAddressDto);

        AddressDto result = getOrderByOrderIdService.GetAddressFromRepository(addressId);

        assertNotNull(result);
        assertEquals(mockAddressDto, result);
        verify(addressesRepository, times(1)).findById(addressId);
        verify(getOrderMapper, times(1)).toAddressDto(mockAddress);
    }

    @Test
    void getResponseBodyDto() {
    }
}