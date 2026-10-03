package com.example.bookstore.order.domain;

import org.springframework.util.Assert;

import java.math.BigDecimal;

public record Money(
        BigDecimal amount,
        String currency
) {
    public Money {
        Assert.notNull(amount, "amount must not be null");
        Assert.notNull(currency, "currency must not be null");
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) >= 0, "amount must be non-negative");
    }

    public Money add(Money money2) {
        if(!this.currency.equals(money2.currency)){
            throw new IllegalArgumentException("different currency cannot be added");
        }
        else {
            return new Money(this.amount.add(money2.amount), currency);
        }
    }

    public Money multiplyPerQuantity(int quantity) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)),this.currency);
    }
    //Factory method used when the order is empty for a zero value.
    public static Money zeroValue(String currency) {
        return new Money(BigDecimal.ZERO,currency);
    }
}
