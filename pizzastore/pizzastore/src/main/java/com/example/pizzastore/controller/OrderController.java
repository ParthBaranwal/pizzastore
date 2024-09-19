package com.example.pizzastore.controller;

import com.example.pizzastore.dto.DeliveryRequest;
import com.example.pizzastore.model.Orders;
import com.example.pizzastore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public Orders getOrderById(@PathVariable Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping("/{orderId}/delivery")
    public ResponseEntity<String> updateDeliveryDetails(
            @PathVariable Long orderId,
            @RequestBody DeliveryRequest deliveryRequest) {

        try {
            orderService.updateOrderDeliveryDetails(orderId, deliveryRequest);
            return ResponseEntity.ok("Delivery details updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> ordersList = orderService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }
}

