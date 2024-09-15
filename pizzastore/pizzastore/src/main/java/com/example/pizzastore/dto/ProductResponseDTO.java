package com.example.pizzastore.dto;

import com.example.pizzastore.model.BeverageSize;
import com.example.pizzastore.model.CrustType;
import com.example.pizzastore.model.PizzaSize;
import com.example.pizzastore.model.Products;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private List<PizzaSize> availablePizzaSizes;
    private List<CrustType> availableCrustTypes;
    private List<BeverageSize> availableBeverageSizes;
    private List<ToppingDTO> toppings;

    // Constructor for Pizza
    public ProductResponseDTO(Long id, String name, BigDecimal price, String description, List<PizzaSize> availablePizzaSizes, List<CrustType> availableCrustTypes,List<ToppingDTO> toppings) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.availablePizzaSizes = availablePizzaSizes;
        this.availableCrustTypes = availableCrustTypes;
        this.toppings = toppings;
    }

    // Constructor for Beverage
    public ProductResponseDTO(Long id, String name, BigDecimal price, String description, List<BeverageSize> availableBeverageSizes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.availableBeverageSizes = availableBeverageSizes;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PizzaSize> getAvailablePizzaSizes() {
        return availablePizzaSizes;
    }

    public void setAvailablePizzaSizes(List<PizzaSize> availablePizzaSizes) {
        this.availablePizzaSizes = availablePizzaSizes;
    }

    public List<CrustType> getAvailableCrustTypes() {
        return availableCrustTypes;
    }

    public void setAvailableCrustTypes(List<CrustType> availableCrustTypes) {
        this.availableCrustTypes = availableCrustTypes;
    }

    public List<BeverageSize> getAvailableBeverageSizes() {
        return availableBeverageSizes;
    }

    public void setAvailableBeverageSizes(List<BeverageSize> availableBeverageSizes) {
        this.availableBeverageSizes = availableBeverageSizes;
    }

    public List<ToppingDTO> getToppings() {
        return toppings;
    }

    public void setToppings(List<ToppingDTO> toppings) {
        this.toppings = toppings;
    }
}
