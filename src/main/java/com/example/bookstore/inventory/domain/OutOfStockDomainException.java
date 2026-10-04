package com.example.bookstore.inventory.domain;


public class OutOfStockDomainException extends RuntimeException {
    private final BookId bookId;

    public OutOfStockDomainException(BookId bookId) {
        super("Not enough copy of book with id " + bookId.bookId());
        this.bookId = bookId;
    }
    public BookId getBookId() {return this.bookId;}
}
