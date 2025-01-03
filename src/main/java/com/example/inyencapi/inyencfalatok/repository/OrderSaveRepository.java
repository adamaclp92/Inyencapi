package com.example.inyencapi.inyencfalatok.repository;

import com.example.inyencapi.inyencfalatok.entity.OrderSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderSaveRepository extends JpaRepository<OrderSave, UUID> {

}
