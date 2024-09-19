package com.example.pizzastore.dto;

import com.example.pizzastore.model.Payment;

import java.time.LocalDateTime;

public class PaymentResponse {
    private Long paymentId;
    private Payment.PaymentStatus paymentStatus;
    private LocalDateTime paymentTime;

    public PaymentResponse(Long paymentId, Payment.PaymentStatus paymentStatus, LocalDateTime paymentTime) {
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
    }

    // Getters and Setters

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Payment.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Payment.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
