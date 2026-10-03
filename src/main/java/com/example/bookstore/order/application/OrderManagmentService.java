package com.example.bookstore.order.application;

/*
è meglio fare in modo che l'ordine nasca vuoto e l'unico punto di ingresso è addOrderItem.
 */

import com.example.bookstore.order.OrderPlacedEvent;
import com.example.bookstore.order.domain.*;
import org.springframework.stereotype.Service;

@Service
public class OrderManagmentService {

    private final OrderRepository orderRepository;

    public OrderManagmentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    void placeOrder(CheckoutRequest request) {
        /*
        Non serve fare la validazione dell'input perchè quando viene fatto il mapping
        agli oggetti di dominio avvengono automaticamente i controlli.
         */
        Order order = new Order(
                new CustomerId(request.customerId()),
                request.address(),
                request.items().get(0).currency()
        );
        for (CheckoutRequest.ItemRequest item: request.items()) {
            order.addOrderItem(
                new BookId(item.bookId()),
                    item.quantity(),
                    new Money(item.price(), item.currency())
            );
        }

        order.place();
        orderRepository.save(order);
    }
}










