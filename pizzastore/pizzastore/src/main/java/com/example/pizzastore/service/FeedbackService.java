package com.example.pizzastore.service;

import com.example.pizzastore.dto.FeedbackDTO;
import com.example.pizzastore.dto.FeedbackRequestDTO;
import com.example.pizzastore.model.Feedback;
import com.example.pizzastore.model.Orders;
import com.example.pizzastore.model.Products;
import com.example.pizzastore.model.User;
import com.example.pizzastore.repository.FeedbackRepository;
import com.example.pizzastore.repository.OrderRepository;
import com.example.pizzastore.repository.ProductsRepository;
import com.example.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsRepository productRepository;

    public FeedbackDTO createFeedback(FeedbackRequestDTO feedbackRequest) {
        // Find the User
        User user = userRepository.findById(feedbackRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the Order
        Orders order = orderRepository.findById(feedbackRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check if the product is in the order
        boolean productExistsInOrder = order.getItems().stream()
                .anyMatch(orderItem -> orderItem.getProduct().getId().equals(feedbackRequest.getProductId()));

        if (!productExistsInOrder) {
            throw new RuntimeException("Product not found in the specified order");
        }

        // Find the Product
        Products product = productRepository.findById(feedbackRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create the Feedback
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setOrder(order);
        feedback.setProduct(product);
        feedback.setOrderRating(feedbackRequest.getOrderRating());
        feedback.setProductRating(feedbackRequest.getProductRating());
        feedback.setComment(feedbackRequest.getComment());
        feedback.setCreatedDate(new Date());

        // Save the feedback
        Feedback savedFeedback = feedbackRepository.save(feedback);

        // Return the FeedbackDTO with the desired fields
        return new FeedbackDTO(
                savedFeedback.getOrder().getId(),
                savedFeedback.getProduct().getId(),
                savedFeedback.getProduct().getName(),
                savedFeedback.getProductRating(),  // Assuming this is the "rating" in your system
                savedFeedback.getComment()
        );
    }

    public List<FeedbackDTO> getFeedbackForOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Map Feedback to FeedbackDTO
        List<FeedbackDTO> feedbackDTOs = new ArrayList<>();
        for (Feedback feedback : order.getFeedbacks()) {
            Products product = feedback.getProduct();
            feedbackDTOs.add(new FeedbackDTO(
                    order.getId(),
                    product.getId(),
                    product.getName(),
                    feedback.getProductRating(), // Use the correct rating field
                    feedback.getComment()
            ));
        }
        return feedbackDTOs;
    }
}
