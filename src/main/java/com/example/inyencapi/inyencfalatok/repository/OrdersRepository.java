package com.example.inyencapi.inyencfalatok.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryRewriter;

import com.example.inyencapi.inyencfalatok.entity.Order;


public interface OrdersRepository extends JpaRepository<Order, Long>{

	@Query("SELECT o FROM Order o ORDER BY o.orderDate DESC LIMIT 1")
	Order getLastOrderElementFromDB();
}
