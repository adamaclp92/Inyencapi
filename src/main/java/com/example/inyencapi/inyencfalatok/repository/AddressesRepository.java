package com.example.inyencapi.inyencfalatok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inyencapi.inyencfalatok.entity.Address;

import java.util.UUID;


public interface AddressesRepository extends JpaRepository<Address, UUID>{

}
