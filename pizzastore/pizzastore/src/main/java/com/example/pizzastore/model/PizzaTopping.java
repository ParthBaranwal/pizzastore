package com.example.pizzastore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pizza_toppings")
public class PizzaTopping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Products pizza;

    @ManyToOne
    @JoinColumn(name = "topping_id")
    private Products topping;

    // Constructors, Getters, and Setters

    public PizzaTopping() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getPizza() {
        return pizza;
    }

    public void setPizza(Products pizza) {
        this.pizza = pizza;
    }

    public Products getTopping() {
        return topping;
    }

    public void setTopping(Products topping) {
        this.topping = topping;
    }
}
