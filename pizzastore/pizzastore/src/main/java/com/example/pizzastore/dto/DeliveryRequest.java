package com.example.pizzastore.dto;

import com.example.pizzastore.model.Address;

public class DeliveryRequest {

    private Long addressId;
    private Address address; // Manually entered address

    // Getters and Setters
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
