package com.example.pizzastore.controller;

import com.example.pizzastore.model.OrderItem;
import com.example.pizzastore.model.Orders;
import com.example.pizzastore.model.Payment;
import com.example.pizzastore.model.PaymentDetails;
import com.example.pizzastore.service.OrderService;
import com.example.pizzastore.service.PaymentService;
import com.example.pizzastore.dto.PaymentRequest;
import com.example.pizzastore.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/payments")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
        Orders order = orderService.getOrderById(paymentRequest.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        // Check if delivery address is set
        if (order.getDeliveryAddress() == null) {
            throw new IllegalArgumentException("Delivery address is not set. Payment cannot be processed.");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        PaymentDetails details = paymentRequest.getPaymentDetails();

        switch (paymentRequest.getPaymentMethod()) {
            case CARD:
                if (details == null || details.getCardNumber() == null || details.getExpiryDate() == null || details.getCvv() == null) {
                    throw new IllegalArgumentException("Card details are incomplete");
                }
                payment.setPaymentDetails(details);
                payment.setPaymentStatus(Payment.PaymentStatus.PAID);
                break;

            case UPI:
                if (details == null || details.getUpiId() == null) {
                    throw new IllegalArgumentException("UPI ID is missing");
                }
                payment.setPaymentDetails(details);
                payment.setPaymentStatus(Payment.PaymentStatus.PAID);
                break;

            case COD:
                payment.setPaymentDetails(null);
                payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
                break;

            default:
                throw new IllegalArgumentException("Unsupported payment method");
        }

        payment.setPaymentTime(LocalDateTime.now());

        Payment savedPayment = paymentService.save(payment);

        // Update order payment status
        orderService.updateOrderPaymentStatus(order.getId(), payment.getPaymentStatus());

        return new PaymentResponse(savedPayment.getId(), savedPayment.getPaymentStatus(), savedPayment.getPaymentTime());
    }
}
