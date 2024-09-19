package com.example.pizzastore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CartItems")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    private int quantity;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @Enumerated(EnumType.STRING)
    private PizzaSize pizzaSize;

    @Enumerated(EnumType.STRING)
    private CrustType crustType;

    @Enumerated(EnumType.STRING)
    private BeverageSize beverageSize;

    public CartItem() {}

    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public CrustType getCrustType() {
        return crustType;
    }

    public void setCrustType(CrustType crustType) {
        this.crustType = crustType;
    }

    public BeverageSize getBeverageSize() {
        return beverageSize;
    }

    public void setBeverageSize(BeverageSize beverageSize) {
        this.beverageSize = beverageSize;
    }
}
