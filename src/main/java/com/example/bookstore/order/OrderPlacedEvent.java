package com.example.bookstore.order;

import java.util.List;
import java.util.UUID;

/*
Deve avere solo le informazioni a cui gli altri sono interessati. Non tutte quelle possibili.
Sta fuori rispetto al resto perchè è un contratto pubblico.
Un evento come questo deve essere generato dall'aggregatore ovvero Order.
L'evento viene pubblicato nel momento in cui invochiamo save() nel repository. Non deve essere Order ha farlo
perchè staremmo inquinando il dominio con parti infrastrutturali.
La dipendenza spring-modulith-starter-jpa permette di garantire la resilienza dell'evento implementando dietro le quinte
l'outbox pattern.
 */
public record OrderPlacedEvent(
        UUID orderId,
        List<OrderLine> items
) {
    public record OrderLine(
            Long bookId,
            int quantity
    ) {}
}
