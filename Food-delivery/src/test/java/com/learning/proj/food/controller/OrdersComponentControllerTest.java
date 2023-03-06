//package com.learning.proj.food.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.learning.proj.food.controller.mappers.OrderMapper;
//import com.learning.proj.food.domain.entity.Food;
//import com.learning.proj.food.domain.entity.Order;
//import com.learning.proj.food.domain.entity.OrderItem;
//import com.learning.proj.food.domain.entity.OrderStatus;
//import com.learning.proj.food.service.order.OrderService;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(OrdersComponentController.class)
//class OrdersComponentControllerTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @MockBean
//    private OrderService orderService;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @SneakyThrows
//    @Test
//    void getOrderById() {
//        //given
//        Order order = new Order();
//        String orderId = "orderIdTest";
//        String customerId = "customerIdTest";
//        String chefId = "chefIdTest";
//        String riderId = "riderIdTest";
//        order.setCreatedCustomerId(customerId);
//        order.setAssignedChefId(chefId);
//        order.setAssignedRiderId(riderId);
//
//        //when
//        when(orderService.findOrderById(orderId)).thenReturn(order);
//
//        //then
//        MvcResult result = this.mockMvc.perform(get("/api/v1/orders/{orderId}", orderId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String orderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(orderJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void createNewOrder() {
//        //given
//        Order order = new Order();
//        String customerId = "customerIdTest";
//        String chefId = "chefIdTest";
//        String riderId = "riderIdTest";
//        order.setCreatedCustomerId(customerId);
//        order.setAssignedChefId(chefId);
//        order.setAssignedRiderId(riderId);
//
//        OrderItem orderItem1 = new OrderItem();
//        Food food1 = new Food();
//        food1.setFoodId("food_1");
//        orderItem1.setFood(food1);
//        orderItem1.setAmount((short) 1);
//
//        OrderItem orderItem2 = new OrderItem();
//        Food food2 = new Food();
//        food2.setFoodId("food_2");
//        orderItem2.setFood(food2);
//        orderItem2.setAmount((short) 1);
//
//        Set<OrderItem> orderItems = new HashSet<>();
//        orderItems.add(orderItem1);
//        orderItems.add(orderItem2);
//        order.setOrderItems(orderItems);
//
//        //when
//        when(orderService.addNewOrder(any())).thenReturn(order);
//
//        //then
//        String createOrderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/orders/create")
//                        .content(createOrderJsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        String orderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(orderJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void updateOrderById() {
//        //given
//        Order order = new Order();
//        String orderId = "orderIdTest";
//        String customerId = "customerIdTest";
//        String chefId = "chefIdTest";
//        String riderId = "riderIdTest";
//        order.setCreatedCustomerId(customerId);
//        order.setAssignedChefId(chefId);
//        order.setAssignedRiderId(riderId);
//
//        OrderItem orderItem1 = new OrderItem();
//        Food food1 = new Food();
//        food1.setFoodId("food_1");
//        orderItem1.setFood(food1);
//        orderItem1.setAmount((short) 1);
//
//        OrderItem orderItem2 = new OrderItem();
//        Food food2 = new Food();
//        food2.setFoodId("food_2");
//        orderItem2.setFood(food2);
//        orderItem2.setAmount((short) 1);
//
//        Set<OrderItem> orderItems = new HashSet<>();
//        orderItems.add(orderItem1);
//        orderItems.add(orderItem2);
//        order.setOrderItems(orderItems);
//
//        //when
//        when(orderService.updateOrder(any(),any())).thenReturn(order);
//
//        //then
//        String orderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/orders/update/{orderId}",orderId)
//                        .content(orderJsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String expectedOrderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedOrderJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void updateOrderStatusByOrderId() {
//        //given
//        Order order = new Order();
//        String orderId = "orderIdTest";
//        String customerId = "customerIdTest";
//        String chefId = "chefIdTest";
//        String riderId = "riderIdTest";
//        order.setCreatedCustomerId(customerId);
//        order.setAssignedChefId(chefId);
//        order.setAssignedRiderId(riderId);
//
//        //when
//        when(orderService.updateOrderStatus(any(),any())).thenReturn(order);
//
//        //then
//        String orderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        MvcResult result = this.mockMvc.perform(post("/api/v1/orders/update/{orderId}/status/{status}",orderId,OrderStatus.CANCELED)
//                        .content(orderJsonString)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String expectedOrderJsonString = objectMapper.writeValueAsString(orderMapper.orderToResponseOrderDTO(order));
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedOrderJsonString);
//    }
//
//    @SneakyThrows
//    @Test
//    void estimateDeliveryTime() {
//        //given
//        String orderId = "orderIdTest";
//        LocalDateTime localDateTime = LocalDateTime.now();
//
//        //when
//        when(orderService.estimateDeliveryTime(any())).thenReturn(localDateTime);
//
//        //then
//        MvcResult result = this.mockMvc.perform(get("/api/v1/orders/estimation/{orderId}",orderId))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //mach LocalDateTime to String with LocaleDateTime response
//        String responseTimeString = result.getResponse().getContentAsString().split("\\.")[0];
//        String expectedTimeString = localDateTime.toString().split("\\.")[0];
//
//        assertThat(responseTimeString).contains(expectedTimeString);
//    }
//}