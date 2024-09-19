package com.example.pizzastore.model;

public class CartItemRequest {
    private Long cartId;
    private Long productId;
    private int quantity;
    private PizzaSize pizzaSize;
    private CrustType crustType;
    private BeverageSize beverageSize;

    // Getters and Setters

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
