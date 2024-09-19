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

    @PostMapping("/payments")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }
}
