package com.example.bookstore.order.application;

/*
è meglio fare in modo che l'ordine nasca vuoto e l'unico punto di ingresso è addOrderItem.
Tipicamente quello che viene fatto è che in un metodo annotato come @Transactional faccio tutti i side-effect.
Questo ha il vantaggio che tutte le varie operazioni fanno parte della stessa transazione, ma ha
anche degli svantaggi. Se faccio diverse operazioni (es. mandare una mail di notifica, aggiorno servizio A,...)
la transazione e ralativa connessione al DB rimane aperta per tutto il tempo.
è anche un problema di isolamento: se mi occuppo dell'ordine mi devo preoccupare solo di quello e non di tutto il resto.
Principio del single responsibility.
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










