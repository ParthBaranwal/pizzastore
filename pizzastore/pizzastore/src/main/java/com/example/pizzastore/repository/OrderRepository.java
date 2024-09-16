package com.example.pizzastore.repository;

import com.example.pizzastore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
