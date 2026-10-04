package com.example.bookstore.inventory.application;

import com.example.bookstore.inventory.domain.BookId;
import com.example.bookstore.inventory.domain.Stock;
import com.example.bookstore.inventory.domain.StockNotFoundException;
import com.example.bookstore.inventory.domain.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockManagmentService {
    private final StockRepository stockRepository;

    public StockManagmentService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock getStockByBookid(BookId bookId) {
        return stockRepository.getStockByBookid(bookId).orElseThrow(
                () -> new StockNotFoundException(bookId)
        );
    }

    /*
    Sarebbe sbagliato se passassi alla funzione l'evento stesso perchè farei dipendere inventory
    da un concetto (evento) che invece deriva da order.
     */
    public void decreaseStock(StockUpdateRequest  stockUpdateRequest) {
        Stock stock = getStockByBookid(new BookId(stockUpdateRequest.bookId()));
        stock.decrease(stockUpdateRequest.quantity());
        stockRepository.save(stock);
    }

    public void increaseStock(StockUpdateRequest  stockUpdateRequest) {
        Stock stock = getStockByBookid(new BookId(stockUpdateRequest.bookId()));
        stock.increase(stockUpdateRequest.quantity());
        stockRepository.save(stock);
    }

    public void createStock(StockCreateRequest stockCreateRequest) {
        stockRepository.save(new Stock(
                new BookId(stockCreateRequest.bookId())
        ));
    }
}
