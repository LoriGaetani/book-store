package com.example.bookstore.inventory.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, BookId> {
    //è assolutamente possibile che non ci sia quindi devo ritornare un Optional altrimenti potrei
    //incappare in NullPointerExceptions. Con .get() posso prendere il valore.
    Optional<Stock> getStockByBookid(BookId bookId);
}
