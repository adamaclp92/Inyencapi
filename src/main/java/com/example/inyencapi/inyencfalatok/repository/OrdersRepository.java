package com.example.inyencapi.inyencfalatok.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryRewriter;

import com.example.inyencapi.inyencfalatok.entity.Order;


public interface OrdersRepository extends JpaRepository<Order, UUID>{

}
