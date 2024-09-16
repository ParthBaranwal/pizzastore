package com.example.pizzastore.controller;

import com.example.pizzastore.model.Order;
import com.example.pizzastore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
