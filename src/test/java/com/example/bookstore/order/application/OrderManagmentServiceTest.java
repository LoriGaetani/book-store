package com.example.bookstore.order.application;

import com.example.bookstore.order.OrderPlacedEvent;
import com.example.bookstore.order.domain.Address;
import com.example.bookstore.order.domain.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderManagmentServiceTest {

/*
Quando eseguo un test con @Test non ho il contesto di Spring che crea in Service e inietta il Repository.
Quindi o mocko il repository: @Mock private OrderRepository orderRepository; @InjectMocks private OrderManagmentService service;
Oppure uso @SpringBootTest e faccio l'autowire del service.
Se voglio fare un test veloce uso H2 poi quando decido di usare Postgres vero con @TestContainer questo
andrà a sovrascrivere H2 senza dover cambiare niente.
 */
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderManagmentService orderManagmentService;

    @Test
    public void testCheckout() {
        CheckoutRequest req = new CheckoutRequest(
                1L,
                new Address("a","b","2","it"),
                List.of(new CheckoutRequest.ItemRequest(
                        1L,2, BigDecimal.valueOf(10),"EUR"),
                        new CheckoutRequest.ItemRequest(
                                2L,1, BigDecimal.valueOf(10),"EUR"))
        );

        orderManagmentService.placeOrder(req);
    }
}
