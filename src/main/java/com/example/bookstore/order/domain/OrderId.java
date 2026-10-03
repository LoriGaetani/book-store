package com.example.bookstore.order.domain;

import java.util.UUID;

public record OrderId(
    UUID id
) {
    public OrderId {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
    }

    public OrderId() {
        this(UUID.randomUUID());
    }
}
