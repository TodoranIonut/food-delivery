package com.learning.proj.food.service.order;

import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Food;
import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.domain.entity.OrderItem;
import com.learning.proj.food.domain.entity.OrderStatus;
import com.learning.proj.food.domain.entity.Restaurant;
import com.learning.proj.food.domain.repository.OrderRepository;
import com.learning.proj.food.utils.DistanceCalculator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.learning.proj.food.utils.Constants.AVERAGE_SPEED_KILOMETERS_PER_HOURS;
import static com.learning.proj.food.utils.Constants.DATE_TIME_ZZ_MM_YYYY_HH_MM_SS_FORMAT;
import static com.learning.proj.food.utils.Constants.MINIMUM_ORDER_DURATION_MINUTES;
import static com.learning.proj.food.utils.DeliveryAppTimeUtils.minutesPerKilometersDistanceAtConstantSpeed;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @SneakyThrows
    @Test
    void findOrderById() {
        //given
        String orderId = "idTest";

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.of(new Order()));
        underTest.findOrderById(orderId);

        //then
        verify(orderRepository).findById(orderId);
    }

    @SneakyThrows
    @Test
    void addNewOrder() {
        //given
        Order givenOrder = new Order();
        givenOrder.setCreatedCustomerId("customerIdTest");
        givenOrder.setRestaurant(new Restaurant());
        givenOrder.setOrderItems(new HashSet<>(Collections.singletonList(new OrderItem())));
        givenOrder.setOrderStatus(OrderStatus.PENDING);
        givenOrder.setDeliveryArea(new DeliveryArea());

        //when
        underTest.addNewOrder(givenOrder);

        //then
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        Order captureFood = orderCaptor.getValue();
        assertThat(givenOrder).isEqualTo(captureFood);
    }

    @SneakyThrows
    @Test
    void updateOrder() {
        //given
        Order givenOrder = new Order();
        givenOrder.setCreatedCustomerId("customerIdTest");
        givenOrder.setRestaurant(new Restaurant());
        givenOrder.setDeliveryArea(new DeliveryArea());
        givenOrder.setOrderItems(new HashSet<>(Collections.singletonList(new OrderItem())));
        givenOrder.setOrderStatus(OrderStatus.CANCELED);

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.of(givenOrder));
        underTest.updateOrder(null, givenOrder);

        //then
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        Order captureFood = orderCaptor.getValue();
        assertThat(givenOrder).isEqualTo(captureFood);
    }

    @SneakyThrows
    @Test
    void updateOrderStatus() {
        //given
        Order givenOrder = new Order();
        givenOrder.setCreatedCustomerId("customerIdTest");
        givenOrder.setRestaurant(new Restaurant());
        givenOrder.setOrderItems(new HashSet<>(Collections.singletonList(new OrderItem())));
        givenOrder.setOrderStatus(OrderStatus.PENDING);

        //when
        when(orderRepository.findById(any())).thenReturn(Optional.of(givenOrder));
        underTest.updateOrderStatus(null, OrderStatus.CANCELED);

        //then
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        Order captureFood = orderCaptor.getValue();
        assertThat(givenOrder).isEqualTo(captureFood);
    }

    @SneakyThrows
    @Test
    void estimatedDelivery(){

        //given
        DeliveryArea customerDeliveryArea = new DeliveryArea(null, 10.0, 10.0, 15000, null);
        DeliveryArea restaurantDeliveryArea = new DeliveryArea(null, 10.0, 10.1, null, null);

        Food food1 = new Food();
        Food food2 = new Food();
        food1.setPreparationMinutes(25);
        food2.setPreparationMinutes(30);

        Restaurant restaurant = new Restaurant(null, "restaurantTestName", restaurantDeliveryArea, new HashSet<>(Arrays.asList(food1,food2)), Collections.singleton(new Chef()));

        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();
        orderItem1.setAmount((short) 2);
        orderItem1.setFood(food1);
        orderItem2.setAmount((short) 3);
        orderItem2.setFood(food2);

        Order order = new Order();
        order.setOrderId("orderId");
        order.setOrderItems(new HashSet<>(Arrays.asList(orderItem1,orderItem2)));
        order.setDeliveryArea(customerDeliveryArea);
        order.setRestaurant(restaurant);
        order.setOrderStatus(OrderStatus.IN_PREPARATION);

        double distanceKilometers = DistanceCalculator.distanceBetweenGeolocationCoordinatesInKilometers(customerDeliveryArea, restaurantDeliveryArea);

        LocalDateTime expectedDate = LocalDateTime.now().plusMinutes(
                food2.getPreparationMinutes()                                                                          //Maximum order item preparation minutes
                        + MINIMUM_ORDER_DURATION_MINUTES                                                                        //minimum minutes
                        + orderItem1.getAmount() + orderItem2.getAmount()                                                       //the amount deviation
                        + minutesPerKilometersDistanceAtConstantSpeed(distanceKilometers, AVERAGE_SPEED_KILOMETERS_PER_HOURS)   //time per distance at speed
        );

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        //then
        LocalDateTime estimatedDate = underTest.estimateDeliveryTime(order.getOrderId());
        assertEquals(estimatedDate.format(DATE_TIME_ZZ_MM_YYYY_HH_MM_SS_FORMAT), expectedDate.format(DATE_TIME_ZZ_MM_YYYY_HH_MM_SS_FORMAT));
    }
}