package com.example.pizzastore.service;

import com.example.pizzastore.model.Address;
import com.example.pizzastore.repository.AddressRepository;
import com.example.pizzastore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> getAddressesByUserId(Long userId) {
        // Ensure the user exists
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return addressRepository.findByUser_UserId(userId);
    }
}
