package com.example.bookstore.order.application;

import com.example.bookstore.inventory.DomainEventListener;
import com.example.bookstore.order.OrderPlacedEvent;
import com.example.bookstore.order.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
Spring Modulith offre l'interfaccia PublishedEvents per poter testare se un evento è stato pubblicato.
Per poter pubblicare gli eventi in modo resiliente serva un DB per l'outbox quindi per i test si usa H2
con ddl-auto che crea quello che serve ogni volta. L'alternativa più robusta è TestContainer.

 */
@ApplicationModuleTest
public class OrderPublishedEventTest {

    @Autowired
    OrderManagmentService orderManagmentService;

    // SpyBean ci permette di spiare il componente reale gestito da Spring
    @MockitoSpyBean
    private DomainEventListener inventoryListener;

    @Test
    void createOrderPublishedEvent(PublishedEvents events) {

        CheckoutRequest req = new CheckoutRequest(
                1L,
                new Address("a","b","2","it"),
                List.of(new CheckoutRequest.ItemRequest(
                                1L,2, BigDecimal.valueOf(10),"EUR"),
                        new CheckoutRequest.ItemRequest(
                                2L,1, BigDecimal.valueOf(10),"EUR"))
        );

        orderManagmentService.placeOrder(req);

        //  Verifica che l'evento sia stato pubblicato
        var publishedEvents = events.ofType(OrderPlacedEvent.class);
        assertThat(publishedEvents).hasSize(1);

        // Puoi anche ispezionare il contenuto dell'evento
        var event = publishedEvents.iterator().next();
        assertThat(event.items().get(0).bookId()).isEqualTo(1L);

        // 2. Verifichiamo che il listener venga chiamato.
        // Essendo @Async, diciamo a Mockito di aspettare fino a 2 secondi (2000 ms)
        // affinché l'invocazione avvenga sul thread separato.
        verify(inventoryListener, timeout(2000).times(1))
                .handle(any(OrderPlacedEvent.class));
    }
}
