package com.example.bookstore.order.domain;

public record Address(
        String street,
        String city,
        String zipCode,
        String country
) {
}
