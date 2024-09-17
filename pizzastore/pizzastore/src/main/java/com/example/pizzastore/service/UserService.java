package com.example.pizzastore.service;

import com.example.pizzastore.dto.AddressRequest;
import com.example.pizzastore.dto.UserRegisterRequest;
import com.example.pizzastore.dto.UserUpdateRequest;
import com.example.pizzastore.model.Address;
import com.example.pizzastore.model.User;
import com.example.pizzastore.repository.UserRepository;
import com.example.pizzastore.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public String registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUserName(userRegisterRequest.getUserName());
        user.setPassword(userRegisterRequest.getPassword());
        // Save user to repository
        userRepository.save(user);
        return "User registered successfully!";
    }



    public User updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Update fields if they are not null
        if (userUpdateRequest.getUserName() != null) {
            user.setUserName(userUpdateRequest.getUserName());
        }
        if (userUpdateRequest.getEmail() != null) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }
        if (userUpdateRequest.getPassword() != null) {
            user.setPassword(userUpdateRequest.getPassword());
        }

        // Handle addresses if provided
        user.getAddresses().clear();
        if (userUpdateRequest.getAddresses() != null) {
            for (AddressRequest addressRequest : userUpdateRequest.getAddresses()) {
                Address address = new Address();
                address.setStreet(addressRequest.getStreet());
                address.setCity(addressRequest.getCity());
                address.setState(addressRequest.getState());
                address.setZipCode(addressRequest.getZipCode());
                address.setUser(user); // Set the user
                user.getAddresses().add(address);
            }
        }

        return userRepository.save(user);
    }




    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
