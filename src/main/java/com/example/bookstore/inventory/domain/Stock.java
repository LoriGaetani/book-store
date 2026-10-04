package com.example.bookstore.inventory.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.springframework.util.Assert;

@Entity
public class Stock {
    @EmbeddedId
    private BookId bookid;
    private int quantity;

    Stock() {}

    public Stock(BookId bookid) {
        this.bookid = bookid;
        this.quantity = 0;
    }

    public void decrease(int quantity) {
        Assert.isTrue(quantity > 0, "Quantity must be greater than 0");
        if (quantity > this.quantity) {
            throw new OutOfStockDomainException(this.bookid);
        } else  {
            this.quantity -= quantity;
        }
    }

    public void increase(int quantity) {
        Assert.isTrue(quantity > 0, "Quantity must be greater than 0");
        this.quantity += quantity;
    }

    public int quantity() {
        return this.quantity;
    }
}
