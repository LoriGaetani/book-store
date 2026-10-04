package com.example.bookstore.inventory;

import com.example.bookstore.inventory.application.StockCreateRequest;
import com.example.bookstore.inventory.application.StockManagmentService;
import com.example.bookstore.inventory.application.StockUpdateRequest;
import com.example.bookstore.inventory.domain.BookId;
import com.example.bookstore.inventory.domain.StockRepository;
import com.example.bookstore.order.OrderPlacedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class OrderEventReceived {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    StockManagmentService stockManagmentService;

    @Test
    public void FakeOrderEventReceived(Scenario scenario) {

        stockManagmentService.createStock(
                new StockCreateRequest(1L)
        );
        assertThat(stockManagmentService.getStockByBookid(new BookId(1L)).quantity()).isEqualTo(0);

        stockManagmentService.increaseStock(
                new StockUpdateRequest(1L, 2)
        );
        assertThat(stockManagmentService.getStockByBookid(new BookId(1L)).quantity()).isEqualTo(2);

        OrderPlacedEvent order = new OrderPlacedEvent(
                UUID.randomUUID(),
                List.of(new OrderPlacedEvent.OrderLine(1L,1))
        );

        //Scenario mi permette di pubblicare l'evento in maniera indipendente
        //Dato che è asincrono mi devo mettere in attesa che il repository ritorni
        scenario.publish(order)
                .andWaitForStateChange(()->stockRepository.getStockByBookid(new BookId(1L)))
                .andVerify(stockOpzionale -> {
                    assertThat(stockOpzionale).isNotNull();
                    assertThat(stockOpzionale.get().quantity()).isEqualTo(1);
                });
    }
}
