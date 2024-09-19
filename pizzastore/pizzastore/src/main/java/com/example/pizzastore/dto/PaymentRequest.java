package com.example.pizzastore.dto;

import com.example.pizzastore.model.Payment;
import com.example.pizzastore.model.PaymentDetails;

public class PaymentRequest {
    private Long orderId;
    private Payment.PaymentMethod paymentMethod;
    private PaymentDetails paymentDetails;

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Payment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
