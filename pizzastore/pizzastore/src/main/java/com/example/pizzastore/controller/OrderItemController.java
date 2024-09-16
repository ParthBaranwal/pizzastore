package com.example.pizzastore.controller;

import com.example.pizzastore.model.OrderItem;
import com.example.pizzastore.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.save(orderItem);
    }

    @GetMapping("/{id}")
    public Optional<OrderItem> getOrderItemById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteById(id);
    }
}
