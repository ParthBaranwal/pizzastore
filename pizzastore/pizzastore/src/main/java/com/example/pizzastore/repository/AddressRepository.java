package com.example.pizzastore.repository;

import com.example.pizzastore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    //List<Address> findByUser_UserId(Long id);
}
