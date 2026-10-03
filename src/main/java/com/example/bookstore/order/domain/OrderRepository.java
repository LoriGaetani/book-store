package com.example.bookstore.order.domain;

import org.springframework.data.repository.CrudRepository;

//CrudRepository e non JpaRepository
public interface OrderRepository extends CrudRepository<Order, OrderId> {


}
