package com.example.inyencapi.inyencfalatok.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inyencapi.inyencfalatok.entity.Meal;

@Repository
public interface MealsRepository extends JpaRepository<Meal, Long>{

}
