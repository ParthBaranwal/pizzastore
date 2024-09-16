package com.example.pizzastore.controller;

import com.example.pizzastore.model.User;
import com.example.pizzastore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserInfo(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> updated = userService.updateUser(id, updatedUser);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
