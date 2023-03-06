package com.learning.proj.food.domain.repository;

import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.domain.entity.OrderStatus;
import com.learning.proj.food.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Set<Order> findAllByRestaurant(Restaurant restaurant);

    Set<Order> findAllByRestaurantAndOrderStatus(Restaurant restaurant,OrderStatus orderStatus);

    Set<Order> findByOrderStatus(OrderStatus orderStatus);

    /**
     * Select only orders that have other status then CANCELED, or DELIVERED
     * @return list of orders
     */
    @Query(
            value = "SELECT * FROM ORDERS " +
                    "WHERE ORDERS.ORDER_STATUS != 'CANCELED' AND ORDERS.ORDER_STATUS != 'DELIVERED'",
            nativeQuery = true
    )
    List<Order> findAllOngoingOrders();

    Order findOrderByAssignedRiderIdAndOrderStatus(String riderId, OrderStatus orderStatus);
}