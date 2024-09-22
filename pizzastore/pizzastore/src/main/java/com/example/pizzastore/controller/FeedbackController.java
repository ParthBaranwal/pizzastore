package com.example.pizzastore.controller;

import com.example.pizzastore.dto.FeedbackDTO;
import com.example.pizzastore.dto.FeedbackRequestDTO;
import com.example.pizzastore.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody FeedbackRequestDTO feedbackRequest) {
        FeedbackDTO feedback = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackForOrder(@PathVariable Long orderId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbackForOrder(orderId);
        return ResponseEntity.ok(feedbacks);
    }
}

