package com.learning.proj.food.service.order;

import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.domain.entity.OrderItem;
import com.learning.proj.food.domain.entity.OrderStatus;
import com.learning.proj.food.domain.entity.Restaurant;
import com.learning.proj.food.domain.repository.OrderRepository;
import com.learning.proj.food.domain.repository.RestaurantRepository;
import com.learning.proj.food.exception.DeliveryApplicationException;
import com.learning.proj.food.exception.exceptionType.BadRequestException;
import com.learning.proj.food.exception.exceptionType.ForbiddenException;
import com.learning.proj.food.exception.order.OrderIdNotFoundException;
import com.learning.proj.food.exception.order.OrderInvalidStatusException;
import com.learning.proj.food.exception.order.OrderMissingItemsException;
import com.learning.proj.food.exception.order.OrdersNotFoundInRestaurantException;
import com.learning.proj.food.exception.order.OrdersReadyForDeliveryNotFoundInAreaException;
import com.learning.proj.food.exception.restaurant.RestaurantIdNotFoundException;
import com.learning.proj.food.exception.restaurant.RestaurantMissingFromDeliveryAreaException;
import com.learning.proj.food.service.user.UserService;
import com.learning.proj.food.utils.DistanceCalculator;
import com.learning.proj.food.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learning.proj.food.utils.Constants.AVERAGE_SPEED_KILOMETERS_PER_HOURS;
import static com.learning.proj.food.utils.Constants.FIND_RIDER_AREA_DISTANCE_METERS;
import static com.learning.proj.food.utils.Constants.MINIMUM_ORDER_DURATION_MINUTES;
import static com.learning.proj.food.utils.DeliveryAppTimeUtils.minutesPerKilometersDistanceAtConstantSpeed;

@EnableScheduling
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;


    public String getAppUserId() throws DeliveryApplicationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().stream().findFirst().isPresent()) {
            String role = principal.getAuthorities().stream().findFirst().get().toString().replace("ROLE_", "");
            AppUser appUser = userService.findAppUserByEmailAndRole(principal.getUsername(), role);
            return appUser.getUserInfo().getId();
        } else
            throw new ForbiddenException("Access forbidden!!!");
    }

    @Override
    public Order findOrderById(String orderId) throws DeliveryApplicationException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderIdNotFoundException(orderId));
    }

    @Override
    public Order findOrderByRiderAndStatus(OrderStatus orderStatus) throws DeliveryApplicationException {
        String riderId = getAppUserId();
        return orderRepository.findOrderByAssignedRiderIdAndOrderStatus(riderId,orderStatus);
    }

    @Override
    public Set<Order> findOrderByRestaurant(String restaurantId) throws DeliveryApplicationException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.debug("restaurant id {} not found", restaurantId);
                    return new RestaurantIdNotFoundException(restaurantId);
                });
        Set<Order> orders = orderRepository.findAllByRestaurant(restaurant);
        if (orders.isEmpty()) {
            throw new OrdersNotFoundInRestaurantException();
        }
        return orders;
    }

    @Override
    public Set<Order> findOrderByRestaurantAndStatus(String restaurantId, OrderStatus orderStatus) throws DeliveryApplicationException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.debug("restaurant id {} not found", restaurantId);
                    return new RestaurantIdNotFoundException(restaurantId);
                });
        Set<Order> orders = orderRepository.findAllByRestaurantAndOrderStatus(restaurant, orderStatus);
        if (orders.isEmpty()) {
            throw new OrdersNotFoundInRestaurantException();
        }
        return orders;
    }

    @Override
    public Order findReadyForDeliveryOrderInDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException {
        Set<Order> orders = orderRepository.findByOrderStatus(OrderStatus.READY_FOR_DELIVERY);
        if (orders.isEmpty())
            throw new OrdersReadyForDeliveryNotFoundInAreaException();

        Optional<Order> order = orders.stream().filter(
                        o -> DistanceCalculator.distanceBetweenGeolocationCoordinatesInMeters(
                                o.getDeliveryArea(), deliveryArea
                        ) < FIND_RIDER_AREA_DISTANCE_METERS)
                .min(Comparator.comparing(Order::getDeliveryEstimationTimestamp));


        if (order.isEmpty()) {
            log.debug("no restaurant in radius {} from coordinates {} lat {} lon", deliveryArea.getLatitude(), deliveryArea.getLongitude());
            throw new OrdersReadyForDeliveryNotFoundInAreaException();
        }
        Order orderInArea = order.get();
        orderInArea.setAssignedRiderId(getAppUserId());
        orderInArea.setOrderStatus(OrderStatus.IN_DELIVERY);
        return orderRepository.save(orderInArea);
    }

    @Override
    public Order addNewOrder(Order order) throws DeliveryApplicationException {
        OrderValidator.validate(order);
        order.setCreatedCustomerId(getAppUserId());
        log.info("add new order {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(String orderId, Order order) throws DeliveryApplicationException {
        OrderValidator.validate(order);
        Order findOrder = findOrderById(orderId);
        log.info("update order id {}", orderId);
        findOrder.setAssignedChefId(order.getAssignedChefId());
        findOrder.setAssignedRiderId(order.getAssignedRiderId());
        findOrder.setOrderItems(order.getOrderItems());
        findOrder.setDeliveryEstimationTimestamp(order.getDeliveryEstimationTimestamp());
        findOrder.setDeliveryArea(order.getDeliveryArea());
        findOrder.setRestaurant(order.getRestaurant());
        findOrder.setOrderStatus(order.getOrderStatus());
        return orderRepository.save(findOrder);
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus orderStatus) throws DeliveryApplicationException {
        Order findOrder = findOrderById(orderId);
        log.info("update order {}", orderId);
        validateNewOrderStatus(findOrder.getOrderStatus(), orderStatus);
        if(orderStatus.equals(OrderStatus.IN_PREPARATION)){
            findOrder.setAssignedChefId(getAppUserId());
        }
        if(orderStatus.equals(OrderStatus.RIDER_PIKING_UP)){
            findOrder.setAssignedRiderId(getAppUserId());
        }
        findOrder.setOrderStatus(orderStatus);
        return orderRepository.save(findOrder);
    }

    /**
     * Estimated delivery will be calculated as a sum of multiple values
     * current time + minim minutes default (pref 15 minutes) + maximum duration on ordered items + 1 min deviation x ordered items + distance * average speed (can be dependent of vehicle)
     * Estimated time should be estimated differently depending on order status
     *
     * @return Date object as order approximate arrival date, build by default constructor Date (long millis)
     */
    @Override
    public LocalDateTime estimateDeliveryTime(String orderId) throws DeliveryApplicationException {

        Order order = findOrderById(orderId);

        DeliveryArea customerLocation = order.getDeliveryArea();
        DeliveryArea restaurantLocation = order.getRestaurant().getDeliveryArea();

        double distance = DistanceCalculator.distanceBetweenGeolocationCoordinatesInKilometers(customerLocation, restaurantLocation);
        int timePerDistanceMinutes = minutesPerKilometersDistanceAtConstantSpeed(distance, AVERAGE_SPEED_KILOMETERS_PER_HOURS);

        long maxOrderDurationMinutes = order.getOrderItems().stream()
                .max(Comparator.comparing(x -> x.getFood().getPreparationMinutes()))
                .orElseThrow(OrderMissingItemsException::new)
                .getFood().getPreparationMinutes();

        int countingDeviationMinutes = order.getOrderItems().stream().mapToInt(OrderItem::getAmount).sum();

        long estimatedMinutesSum = MINIMUM_ORDER_DURATION_MINUTES;

        switch (order.getOrderStatus()) {
            case CANCELED:
                estimatedMinutesSum = 0L;
                break;
            case IN_DELIVERY:
                estimatedMinutesSum = timePerDistanceMinutes;
                break;
            case IN_PREPARATION:
                estimatedMinutesSum += timePerDistanceMinutes + maxOrderDurationMinutes + countingDeviationMinutes;
                break;
            case READY_FOR_DELIVERY:
                estimatedMinutesSum += timePerDistanceMinutes;
                break;
        }

        LocalDateTime localDateTimeEstimation = LocalDateTime.now().plusMinutes(estimatedMinutesSum);
        long estimatedTimestamp = Timestamp.valueOf(localDateTimeEstimation).getTime();

        order.setDeliveryEstimationTimestamp(estimatedTimestamp);
        orderRepository.save(order);

        return localDateTimeEstimation;
    }

    private void validateNewOrderStatus(OrderStatus orderStatus, OrderStatus newOrderStatus) throws BadRequestException {
        if (!newOrderStatus.equals(OrderStatus.CANCELED)
                && !newOrderStatus.equals(orderStatus.getNextStatus())) {
            throw new OrderInvalidStatusException("Order invalid status!");
        }
    }

    private Set<Restaurant> findAllOrdersByDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException {
        Set<Restaurant> restaurantsInArea = new HashSet<>(restaurantRepository.findAll());
        if (!restaurantsInArea.isEmpty()) {
            restaurantsInArea = restaurantsInArea.stream().filter(
                            restaurant -> DistanceCalculator.distanceBetweenGeolocationCoordinatesInMeters(
                                    restaurant.getDeliveryArea(), deliveryArea
                            ) < restaurant.getDeliveryArea().getRadius())
                    .collect(Collectors.toSet());
        }
        if (restaurantsInArea.isEmpty()) {
            log.debug("no restaurant in radius {} from coordinates {} lat {} lon", deliveryArea.getRadius(), deliveryArea.getLatitude(), deliveryArea.getLongitude());
            throw new RestaurantMissingFromDeliveryAreaException(deliveryArea);
        }
        return restaurantsInArea;
    }

    @Transactional
    @Scheduled(fixedDelay = 20000, initialDelay = 5000)
    public void estimateDeliveryTimeScheduler() {
        List<Order> ongoingOrders = orderRepository.findAllOngoingOrders();
        if (!ongoingOrders.isEmpty()) {
            for (Order order : ongoingOrders) {
                try {
                    estimateDeliveryTime(order.getOrderId());
                } catch (DeliveryApplicationException ignore) {
                }
            }
        }
    }
}
