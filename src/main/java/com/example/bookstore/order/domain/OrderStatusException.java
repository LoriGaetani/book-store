package com.example.bookstore.order.domain;

/*
public class OrderStatusException extends RuntimeException{
    private final OrderStatus orderStatusActual;
    private final String functionCalled;
    private final OrderId orderId;

    private OrderStatusException(OrderStatus orderStatusActual,
                                String functionCalled,
                                OrderId orderId
                                ) {
        super("Order with id: "+orderId+" is in state "+ orderStatusActual +" which is invalid. " +
                "for method "+functionCalled);
        this.orderStatusActual = orderStatusActual;
        this.functionCalled = functionCalled;
        this.orderId = orderId;
    }
    public OrderStatus getOrderStatusActual() {
        return orderStatusActual;
    }
    public String getFunctionCalled() {
        return functionCalled;
    }
    public OrderId getOrderId() {
        return orderId;
    }

    //factory per passare il metodo che ha scatenato l'eccezione
    public static OrderStatusException OrderStatusExceptionForAddOrderItem(
            OrderStatus orderStatusActual,
            OrderId orderId) {
        return new OrderStatusException(orderStatusActual,"addOrderItem",orderId);
    }
}

 */

public class OrderStatusException extends RuntimeException {

    private final OrderId orderId;
    private final OrderStatus actualStatus;
    private final OrderOperations operation;

    public OrderStatusException(OrderId orderId, OrderStatus actualStatus, OrderOperations operation) {
        super("Cannot %s order %s: the order is in state %s"
                .formatted(operation, orderId.id(), actualStatus));
        this.orderId = orderId;
        this.actualStatus = actualStatus;
        this.operation = operation;
    }

    public OrderId orderId()            { return orderId; }
    public OrderStatus actualStatus()   { return actualStatus; }
    public OrderOperations operation()   { return operation; }
}



