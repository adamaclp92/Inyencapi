package com.example.inyencapi.inyencfalatok.controller;

import com.example.inyencapi.inyencfalatok.dto.AddressDto;
import com.example.inyencapi.inyencfalatok.dto.CustomerDto;
import com.example.inyencapi.inyencfalatok.dto.MealQuantityDto;
import com.example.inyencapi.inyencfalatok.dto.PostNewOrderRequestBodyDto;
import com.example.inyencapi.inyencfalatok.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderController.class)
class OrderControllerTest {
    private static final CustomerDto customer = new CustomerDto("John Doe", "+30901234567", "test@test.com");
    private static final AddressDto address = new AddressDto(1111, "Budapest", "Vaci ut 11111.");
    private static final MealQuantityDto meal1 = new MealQuantityDto(UUID.fromString("e689ab29-abd0-11ef-8d70-0242ac110002"), 1);
    private static final MealQuantityDto meal2 = new MealQuantityDto(UUID.fromString("e689af67-abd0-11ef-8d70-0242ac110002"), 2);
    private static final MealQuantityDto meal3 = new MealQuantityDto(UUID.fromString("e689b1a4-abd0-11ef-8d70-0242ac110002"), 3);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaProducer kafkaProducer;

    @Autowired
    private ObjectMapper objectMapper;

    private PostNewOrderRequestBodyDto validRequestBody;

    private PostNewOrderRequestBodyDto invalidRequestBody;


    @BeforeEach
    void setup() {
        validRequestBody = new PostNewOrderRequestBodyDto();
        validRequestBody.setCustomerDatas(customer);
        validRequestBody.setCustomerAddress(address);
        validRequestBody.addMealItemsItem(meal1);
        validRequestBody.addMealItemsItem(meal2);
        validRequestBody.addMealItemsItem(meal2);

        invalidRequestBody = new PostNewOrderRequestBodyDto();
        invalidRequestBody.setCustomerAddress(address);
        invalidRequestBody.addMealItemsItem(meal1);
        invalidRequestBody.addMealItemsItem(meal2);
        invalidRequestBody.addMealItemsItem(meal2);
    }

    @Test
    void writeMessageToTopic_OK() throws Exception{
        mockMvc.perform(post("/api/orders")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestBody))
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Rendeles leadva: %s", validRequestBody.toString())));

        verify(kafkaProducer).sendNewOrderMessage(Mockito.eq(validRequestBody));
    }

    @Test
    void writeMessageToTopic_Unauthorized() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "wongpassword"))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isUnauthorized());
    }

    @Test
    void writeMessageToTopic_IncompleteBody() throws Exception{
        invalidRequestBody.setCustomerDatas(null);

        mockMvc.perform(post("/api/orders")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestBody))
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest())
                .andExpect(content().string("customer_datas property is missing!"));
    }
}

