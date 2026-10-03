package com.example.bookstore.order;

import com.example.bookstore.order.domain.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

// se rispetto lo stesso package del codice posso accedere ai metodi che hanno visibilità protected
public class OrderTest {

    /*
    @Test
    void testCalculateTotal() {
        Money somma1 = new Money(BigDecimal.valueOf(11.5),"EU");
        Money somma2 = new Money(BigDecimal.valueOf(7.33),"EU");
        OrderItem item1 = new OrderItem(
                new BookId(1L),
                2,
                somma1);
        OrderItem item2 = new OrderItem(
                new BookId(2L),
                1,
                somma2);
        Order order = new Order(
                new CustomerId(1L),
                new Address("a","b","2","it"),
                List.of(item1,item2)
        );

        assertEquals(new Money(BigDecimal.valueOf(30.33),"EU"),order.getTotal());
    }
     */

    @Test
    void addOrderItemCorrectly() {
        Order order = new Order(
                new CustomerId(1L),
                new Address("a","b","2","it"),
                "EUR"
        );
        order.addOrderItem(
                new BookId(1L),
                2,
                new Money(BigDecimal.valueOf(11.5),"EUR")
        );
        assertEquals(BigDecimal.valueOf(23.0),order.getTotal().amount());
    }
}
