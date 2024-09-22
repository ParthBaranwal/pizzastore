package com.example.pizzastore.dto;

public class FeedbackDTO {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer rating;
    private String comment;

    // Constructors
    public FeedbackDTO(Long orderId, Long productId, String productName, Integer rating, String comment) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
