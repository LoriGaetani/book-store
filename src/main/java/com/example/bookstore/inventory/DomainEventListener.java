package com.example.bookstore.inventory;

import com.example.bookstore.order.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class DomainEventListener {

    private final Logger logger = LoggerFactory.getLogger(DomainEventListener.class);

    DomainEventListener() {

    }

    //l'evento lo prendo dall'altro modulo perchè è pubblico
    @ApplicationModuleListener
    public void handle(OrderPlacedEvent orderPlacedEvent) {
        logger.info("Received OrderPlacedEvent {}", orderPlacedEvent);
    }
}
