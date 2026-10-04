package com.example.bookstore.inventory;

import com.example.bookstore.inventory.domain.BookId;
import com.example.bookstore.inventory.domain.OutOfStockDomainException;
import com.example.bookstore.inventory.domain.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockTest {

    @Test
    public void decreaseStockQuantityOk() {
        Stock stock = new Stock(
                new BookId(1L)
        );
        stock.increase(2);
        stock.decrease(1);
        assertEquals(1,stock.quantity());
    }

    @Test
    public void outOfStockException() {
        Stock stock = new Stock(
                new BookId(1L)
        );

        assertThrows(OutOfStockDomainException.class,() -> stock.decrease(3));
    }
}
