package com.example.inyencapi.inyencfalatok.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inyencapi.inyencfalatok.entity.OrderItem;

import java.util.UUID;

@Repository
public interface Order_ItemsRepository extends JpaRepository<OrderItem, UUID>{

}
