package com.gateway.gatewaysecurity.domain.entity;

public enum OrderStatus {

    NULL_STATUS(null),
    CANCELED(NULL_STATUS),
    DELIVERED(NULL_STATUS),
    IN_DELIVERY(DELIVERED),
    RIDER_PIKING_UP(IN_DELIVERY),
    READY_FOR_DELIVERY(RIDER_PIKING_UP),
    IN_PREPARATION(READY_FOR_DELIVERY),
    PENDING(IN_PREPARATION);

    OrderStatus nextStatus;

    OrderStatus(OrderStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public OrderStatus getNextStatus() {
        return this.nextStatus;
    }


}
