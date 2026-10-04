package com.example.bookstore.inventory.domain;

public class StockNotFoundException extends RuntimeException {
    private final BookId bookId;

    public StockNotFoundException(BookId bookId) {
        super("No stock with book_id " + bookId + " found");
        this.bookId = bookId;
    }
    public BookId getBookId() {return bookId;}
}
