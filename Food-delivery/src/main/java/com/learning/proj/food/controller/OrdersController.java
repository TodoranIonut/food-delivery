package com.learning.proj.food.controller;

import com.learning.proj.food.controller.dto.order.CreateOrderDTO;
import com.learning.proj.food.controller.dto.order.OrderDTO;
import com.learning.proj.food.controller.dto.order.ResponseOrderDTO;
import com.learning.proj.food.controller.dto.restaurant.LocationDTO;
import com.learning.proj.food.controller.mappers.DeliveryAreaMapper;
import com.learning.proj.food.controller.mappers.OrderMapper;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.domain.entity.OrderStatus;
import com.learning.proj.food.exception.DeliveryApplicationException;
import com.learning.proj.food.service.order.OrderService;
import com.learning.proj.food.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final DeliveryAreaMapper deliveryAreaMapper;

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','CHEF','RIDER')")
    public ResponseEntity<ResponseOrderDTO> getOrderById(@PathVariable @NotNull String orderId) throws DeliveryApplicationException {
        Order order = orderService.findOrderById(orderId);
        ResponseOrderDTO responseOrderDTO = orderMapper.orderToResponseOrderDTO(order);
        return ResponseEntity.ok().body(responseOrderDTO);
    }

    @GetMapping("/pendingOrders/{restaurantId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','CHEF')")
    public ResponseEntity<Set<ResponseOrderDTO>> getPendingOrders(@PathVariable @NotNull String restaurantId) throws DeliveryApplicationException {
        Set<Order> order = orderService.findOrderByRestaurantAndStatus(restaurantId, OrderStatus.PENDING);
        Set<ResponseOrderDTO> responseOrderDTO = orderMapper.orderSetToResponseOrderDTOSet(order);
        return ResponseEntity.ok().body(responseOrderDTO);
    }

    @PostMapping("/readyForPickUp")
    @PreAuthorize("hasAnyRole('ADMIN','RIDER')")
    public ResponseEntity<ResponseOrderDTO> getReadyForDeliveryOrders(@RequestBody @NotNull LocationDTO locationDTO) throws DeliveryApplicationException {
        DeliveryArea deliveryArea = deliveryAreaMapper.locationDTOToDeliveryArea(locationDTO);
        Order order = orderService.findReadyForDeliveryOrderInDeliveryArea(deliveryArea);
        ResponseOrderDTO responseOrderDTO = orderMapper.orderToResponseOrderDTO(order);
        return ResponseEntity.ok().body(responseOrderDTO);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<ResponseOrderDTO> createNewOrder(@RequestBody @NotNull CreateOrderDTO createOrderDTO) throws DeliveryApplicationException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/orders/create").toUriString());
        Order order = orderMapper.createdOrderDTOToOrder(createOrderDTO);
        Order createdOrder = orderService.addNewOrder(order);
        ResponseOrderDTO responseOrderDTO = orderMapper.orderToResponseOrderDTO(createdOrder);
        return ResponseEntity.created(uri).body(responseOrderDTO);
    }

    @PutMapping("/update/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN','CHEF')")
    public ResponseEntity<ResponseOrderDTO> updateOrderById(@PathVariable @NotNull String orderId, @RequestBody @NotNull OrderDTO orderDTO) throws DeliveryApplicationException {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        Order updated = orderService.updateOrder(orderId, order);
        ResponseOrderDTO responseOrderDTO = orderMapper.orderToResponseOrderDTO(updated);
        return ResponseEntity.ok().body(responseOrderDTO);
    }

    @PostMapping("/update/{orderId}/status/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','CHEF')")
    public ResponseEntity<ResponseOrderDTO> updateOrderStatusCanceledByOrderId(@PathVariable @NotNull String orderId) throws DeliveryApplicationException {
        Order order = orderService.updateOrderStatus(orderId, OrderStatus.CANCELED);
        ResponseOrderDTO orderDTO = orderMapper.orderToResponseOrderDTO(order);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/update/{orderId}/status/preparation")
    @PreAuthorize("hasAnyRole('ADMIN','CHEF')")
    public ResponseEntity<ResponseOrderDTO> setInPreparationOrderStatusById(@PathVariable @NotNull String orderId) throws DeliveryApplicationException {
        Order updated = orderService.updateOrderStatus(orderId,OrderStatus.IN_PREPARATION);
        ResponseOrderDTO orderDTO = orderMapper.orderToResponseOrderDTO(updated);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/update/{orderId}/status/readyForDelivery")
    @PreAuthorize("hasAnyRole('ADMIN','CHEF')")
    public ResponseEntity<ResponseOrderDTO> setReadyForDeliveryOrderStatusById(@PathVariable @NotNull String orderId) throws DeliveryApplicationException {
        Order order = orderService.updateOrderStatus(orderId, OrderStatus.READY_FOR_DELIVERY);
        ResponseOrderDTO orderDTO = orderMapper.orderToResponseOrderDTO(order);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/update/{orderId}/status/pikingUp")
    @PreAuthorize("hasAnyRole('ADMIN','RIDER')")
    public ResponseEntity<ResponseOrderDTO> setPikingUpOrderStatusById(@PathVariable @NotNull String orderId) throws DeliveryApplicationException {
        Order order = orderService.updateOrderStatus(orderId, OrderStatus.RIDER_PIKING_UP);
        ResponseOrderDTO orderDTO = orderMapper.orderToResponseOrderDTO(order);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/update/{orderId}/status/delivered")
    @PreAuthorize("hasAnyRole('ADMIN','RIDER')")
    public ResponseEntity<ResponseOrderDTO> setDeliveredOrderStatusById(@PathVariable String orderId) throws DeliveryApplicationException {
        Order order = orderService.updateOrderStatus(orderId, OrderStatus.DELIVERED);
        ResponseOrderDTO orderDTO = orderMapper.orderToResponseOrderDTO(order);
        return ResponseEntity.ok().body(orderDTO);
    }

    @GetMapping("/estimation/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER','CHEF','RIDER')")
    public ResponseEntity<LocalDateTime> estimateDeliveryTime(@PathVariable String orderId) throws DeliveryApplicationException {
        LocalDateTime localDateTime = orderService.estimateDeliveryTime(orderId);
        return ResponseEntity.ok().body(localDateTime);
    }
}
