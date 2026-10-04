package com.example.bookstore.inventory.application;

public record StockUpdateRequest(
        Long bookId,
        int quantity
) {
}
