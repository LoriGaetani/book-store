package com.example.bookstore.order.domain;

import com.example.bookstore.order.OrderPlacedEvent;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import org.springframework.util.Assert;

import java.util.UUID;

/*
 Deve essere un value object e non una entità. Questo per due motivi:
    1. Non ha un'identità propria, ma è identificato dall'ordine a cui appartiene.
    2. Due OrderItem con gli stessi valori di bookId, quantity e unitPrice sono considerati uguali.
 Dato che non è un'entità distinta con un valore proprio la metto come record
 */
@Embeddable
public record OrderItem(
        //UUID id; non ha un valore in sè come oggetto.
        @Embedded
        BookId bookId,
        int quantity,
        @Embedded
        Money unitPrice
) {

    public OrderItem(BookId bookId, int quantity, Money unitPrice){
        Assert.notNull(bookId,"bookId must not be null");
        Assert.notNull(quantity,"quantity must not be null");
        Assert.isTrue(quantity>0,"quantity must be greater than 0");

        //this.id = UUID.randomUUID();
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Money total() {
        return unitPrice.multiplyPerQuantity(quantity);
    }

    /*
    Permette di mappare facilmente anche se sto facendo dipendere il dominio al formato di un evento.
     */
    public OrderPlacedEvent.OrderLine mapToOrderLine() {
        return new OrderPlacedEvent.OrderLine(
                this.bookId.bookId(),
                this.quantity
        );
    }
}

