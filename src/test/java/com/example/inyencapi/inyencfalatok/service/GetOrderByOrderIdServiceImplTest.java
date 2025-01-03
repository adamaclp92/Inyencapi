package com.example.inyencapi.inyencfalatok.service;

import com.example.inyencapi.inyencfalatok.dto.AddressDto;
import com.example.inyencapi.inyencfalatok.entity.Customer;
import com.example.inyencapi.inyencfalatok.entity.Order;
import com.example.inyencapi.inyencfalatok.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetOrderByOrderIdServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private GetOrderByOrderIdServiceImpl getOrderByOrderIdService;

    private UUID orderId;
    private UUID customerId;
    private UUID addressId;
    private Order mockOrder;
    private Customer mockCustomer;
    private AddressDto mockAddressDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        addressId = UUID.randomUUID();

        mockOrder = new Order();
        mockCustomer = new Customer();
        mockAddressDto = new AddressDto();
    }

    @Test
    void getOrderFromRepository() {
      /*  when(ordersRepository.findAll()).thenReturn(mockOrder);

        Order result = getOrderByOrderIdService.GetOrderFromRepository(orderId);

        assertNotNull(result);
        assertEquals(mockOrder, result);
        verify(ordersRepository, times(1)).findById(orderId);*/
    }

    @Test
    void mapOrderToOrderDto() {
    }

    @Test
    void getMealsFromOrderItemList() {
    }

    @Test
    void getCustomerFromRepository() {
    }

    @Test
    void mapCustomerToCustomerDto() {
    }

    @Test
    void getAddressFromRepository() {
    }

    @Test
    void getResponseBodyDto() {
    }
}