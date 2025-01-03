package com.example.inyencapi.inyencfalatok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inyencapi.inyencfalatok.entity.Customer;

import java.util.UUID;


public interface CustomersRepository extends JpaRepository<Customer, UUID>{

}
