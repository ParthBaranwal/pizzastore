package com.example.pizzastore.repository;

import com.example.pizzastore.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Custom query methods can be added here
}
