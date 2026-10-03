package com.example.bookstore.order.application;

import com.example.bookstore.order.domain.Address;

import java.math.BigDecimal;
import java.util.List;

/*è un DTO. Sta fuori dal dominio. Quindi non deve avere metodi associati.
Qua uno tipi primitvi invece che gli oggetti di dominio così non devono sapere come questi sono costruiti.
Per Address invece è diverso perchè ha lo stesso significato sia nel mio dominio che fuori e inoltre non
ha nessun comportamento associato.
 */
public record CheckoutRequest(
    Long customerId,
    Address address,
    List<ItemRequest> items
) {
    public record ItemRequest(Long bookId, int  quantity, BigDecimal price, String currency) {
    }
}









