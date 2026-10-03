package com.example.bookstore.order.domain;

public class EmptyOrderException extends RuntimeException {
    private final OrderId orderId;
    public EmptyOrderException(OrderId orderId) {
        super("Order with id " + orderId + " is empty");
        this.orderId = orderId;
    }
}
