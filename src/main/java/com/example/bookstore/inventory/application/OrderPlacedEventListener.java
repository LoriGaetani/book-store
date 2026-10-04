package com.example.bookstore.inventory.application;

import com.example.bookstore.order.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedEventListener {

    private final StockManagmentService stockManagmentService;

    private final Logger logger = LoggerFactory.getLogger(OrderPlacedEventListener.class);

    OrderPlacedEventListener(StockManagmentService stockManagmentService) {
        this.stockManagmentService = stockManagmentService;
    }

    //l'evento lo prendo dall'altro modulo perchè è pubblico
    //dentro usa le annotazioni @Async,@Transactional,@TransactionalEventLister.
    /*
    Significano che esegue il metodo su un thread separato. Usa una nuova transazione in modo
    che il listener possa fallire e chi pubblica no.
    •@EventListener: reagisce alla pubblicazione immediata dell’evento;
    •@TransactionalEventListener: reagisce in una fase della transazione, normalmente dopo il commit;
    Devo usare @EventListener se mi serve una reazione immediata sincrona. Se mi serve un risultato subito
    per poter andare avanti.
     */
    @ApplicationModuleListener
    public void handle(OrderPlacedEvent orderPlacedEvent) {
        logger.info("Received OrderPlacedEvent {}", orderPlacedEvent);

        orderPlacedEvent
                .items()
                .forEach(item -> {
                    stockManagmentService.decreaseStock(new StockUpdateRequest(
                        item.bookId(), item.quantity()
                    ));
                });
    }
}
