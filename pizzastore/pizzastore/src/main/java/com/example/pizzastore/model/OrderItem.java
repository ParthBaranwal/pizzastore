package com.example.pizzastore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    private int quantity;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PizzaSize pizzaSize;

    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CrustType crustType;

    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BeverageSize beverageSize;

    // Getters and Setters

    @JsonProperty("pizzaSize")
    public PizzaSize getPizzaSize() {
        if (product != null && product.getCategory() == Category.PIZZA) {
            return pizzaSize;
        }
        return null;
    }

    @JsonProperty("crustType")
    public CrustType getCrustType() {
        if (product != null && product.getCategory() == Category.PIZZA) {
            return crustType;
        }
        return null;
    }

    @JsonProperty("beverageSize")
    public BeverageSize getBeverageSize() {
        if (product != null && product.getCategory() == Category.BEVERAGE) {
            return beverageSize;
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public void setCrustType(CrustType crustType) {
        this.crustType = crustType;
    }

    public void setBeverageSize(BeverageSize beverageSize) {
        this.beverageSize = beverageSize;
    }
}
