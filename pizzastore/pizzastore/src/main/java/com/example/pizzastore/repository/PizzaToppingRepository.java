package com.example.pizzastore.repository;

import com.example.pizzastore.model.PizzaTopping;
import com.example.pizzastore.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaToppingRepository extends JpaRepository<PizzaTopping, Long> {
    List<PizzaTopping> findByPizza(Products pizza);
}
