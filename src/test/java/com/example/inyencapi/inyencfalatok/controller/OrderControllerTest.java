package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.*;
import com.example.inyencapi.inyencfalatok.kafka.OrderProducer;
import com.example.inyencapi.inyencfalatok.service.GetOrderByOrderIdServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.example.inyencapi.inyencfalatok.enums.OrderStatus.Feldolgozas_alatt;


@WebMvcTest(OrderController.class)
class OrderControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerTest.class);

    private static final CustomerDto customer = new CustomerDto("John Doe", "+30901234567", "test@test.com");
    private static final AddressDto address = new AddressDto(1111, "Budapest", "Vaci ut 11111.");
    private static final MealQuantityDto meal1 = new MealQuantityDto(UUID.fromString("e689ab29-abd0-11ef-8d70-0242ac110002"), 1);
    private static final MealQuantityDto meal2 = new MealQuantityDto(UUID.fromString("e689af67-abd0-11ef-8d70-0242ac110002"), 2);
    private static final MealQuantityDto meal3 = new MealQuantityDto(UUID.fromString("e689b1a4-abd0-11ef-8d70-0242ac110002"), 3);
    private static final OrderDto order = new OrderDto("6dc33d3c-86f8-4d25-8c9d-d43359d4faad", new Timestamp(System.currentTimeMillis()), Feldolgozas_alatt);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderProducer kafkaProducer;

    @MockBean
    private GetOrderByOrderIdServiceImpl getOrderByOrderIdServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private PostNewOrderRequestBodyDto validPostNewOrderRequestBody;

    private PostNewOrderRequestBodyDto invalidPostNewOrderRequestBody;
    
    private PostNewOrderResponseBodyDto validPostNewOrderResponseBody;

    private GetOrderByOrderIdResponseBodyDto validGetOrderByOrderIdResponseBody;


    @BeforeEach
    void setup() {
        validPostNewOrderRequestBody = new PostNewOrderRequestBodyDto();
        List<MealQuantityDto> mealItems = new ArrayList<>();
        mealItems.add(meal1);
        mealItems.add(meal2);
        mealItems.add(meal3);

        validPostNewOrderRequestBody.setMealItems(mealItems);
        validPostNewOrderRequestBody.setCustomerDatas(customer);
        validPostNewOrderRequestBody.setCustomerAddress(address);

        invalidPostNewOrderRequestBody = new PostNewOrderRequestBodyDto();
        invalidPostNewOrderRequestBody.setMealItems(mealItems);
        invalidPostNewOrderRequestBody.setCustomerAddress(address);
        invalidPostNewOrderRequestBody.setCustomerDatas(null);

        validPostNewOrderResponseBody = new PostNewOrderResponseBodyDto();
        validPostNewOrderResponseBody.setStatus(Feldolgozas_alatt);
        validPostNewOrderResponseBody.setOrderId(UUID.randomUUID());

        validGetOrderByOrderIdResponseBody = new GetOrderByOrderIdResponseBodyDto();
        validGetOrderByOrderIdResponseBody.setOrderDatas(order);
        validGetOrderByOrderIdResponseBody.setCustomerAddress(address);
        validGetOrderByOrderIdResponseBody.setMealItems(mealItems);
        validGetOrderByOrderIdResponseBody.setCustomerDatas(customer);
        
        
    }

    @Test
    void postNewOrder_OK() throws Exception{
        LOGGER.info(String.valueOf(validPostNewOrderRequestBody));
        
        when(kafkaProducer.postNewOrderRequest(validPostNewOrderRequestBody)).thenReturn(validPostNewOrderResponseBody);

        MvcResult result  = mockMvc.perform(post("/api/orders")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPostNewOrderRequestBody))
                        .accept(MediaType.APPLICATION_JSON))

                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status").exists())
                       .andExpect(jsonPath("$.order_id").exists())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LOGGER.info(responseBody);

    }

    @Test
    void postNewOrder_Unauthorized() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "wrongpassword"))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isUnauthorized());
    }

    @Test
    void postNewOrder_MissingBodyField() throws Exception{

        MvcResult result  = mockMvc.perform(post("/api/orders")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostNewOrderRequestBody))
                        .accept(MediaType.APPLICATION_JSON))


                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_code").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.error_message").value("customer_datas property is missing!"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LOGGER.info(responseBody);
    }

    @Test
    void getOrderByOrderId_OK() throws Exception{
        UUID orderId = UUID.fromString("6dc33d3c-86f8-4d25-8c9d-d43359d4faad");
        when(getOrderByOrderIdServiceImpl.GetResponseBodyDto(orderId)).thenReturn(validGetOrderByOrderIdResponseBody);

        mockMvc.perform(get("/api/orders/{orderId}", orderId)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_datas").exists())
                .andExpect(jsonPath("$.customer_address").exists())
                .andExpect(jsonPath("$.order_datas").exists())
                .andExpect(jsonPath("$.meal_items").exists())
                .andReturn();
    }

    @Test
    void getOrderByOrderId_Unauthorized() throws Exception {
        UUID orderId = UUID.fromString("6dc33d3c-86f8-4d25-8c9d-d43359d4faad");
        mockMvc.perform(get("/api/orders/{orderId}", orderId)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "wrongpassword"))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isUnauthorized());
    }


}


