package com.example.pizzastore.dto;

import com.example.pizzastore.model.BeverageSize;
import com.example.pizzastore.model.CrustType;
import com.example.pizzastore.model.PizzaSize;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields globally
public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
    private BigDecimal totalPrice;

    @JsonProperty("pizzaSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PizzaSize pizzaSize;

    @JsonProperty("crustType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CrustType crustType;

    @JsonProperty("beverageSize")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BeverageSize beverageSize;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
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
