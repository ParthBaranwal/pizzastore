package com.example.pizzastore.service;

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

    public String registerUser(User user) {
        if (user.getUserId() != null) {
            throw new IllegalArgumentException("User ID should not be provided in the request.");
        }

        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            return "User already exists";
        }

        userRepository.save(user);
        return "User registered successfully";
    }



        public User updateUser(Long userId, User updatedUser) {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            if (updatedUser.getUserName() != null) {
                existingUser.setUserName(updatedUser.getUserName());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            // Handle address updates
            if (updatedUser.getAddresses() != null) {
                List<Address> existingAddresses = existingUser.getAddresses();
                List<Address> updatedAddresses = updatedUser.getAddresses();

                // Update existing addresses or add new ones
                for (Address updatedAddress : updatedAddresses) {
                    if (updatedAddress.getId() != null) {
                        Address existingAddress = existingAddresses.stream()
                                .filter(address -> address.getId().equals(updatedAddress.getId()))
                                .findFirst()
                                .orElse(null);
                        if (existingAddress != null) {
                            existingAddress.setStreet(updatedAddress.getStreet());
                            existingAddress.setCity(updatedAddress.getCity());
                            existingAddress.setState(updatedAddress.getState());
                            existingAddress.setPostalCode(updatedAddress.getPostalCode());
                            existingAddress.setCountry(updatedAddress.getCountry());
                        } else {
                            updatedAddress.setUser(existingUser);
                            existingAddresses.add(updatedAddress);
                        }
                    } else {
                        updatedAddress.setUser(existingUser);
                        existingAddresses.add(updatedAddress);
                    }
                }

                // Remove addresses that are no longer present
                existingAddresses.removeIf(existingAddress -> updatedAddresses.stream()
                        .noneMatch(updatedAddress -> updatedAddress.getId() != null &&
                                updatedAddress.getId().equals(existingAddress.getId())));
            }

            return userRepository.save(existingUser);
        }



    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
