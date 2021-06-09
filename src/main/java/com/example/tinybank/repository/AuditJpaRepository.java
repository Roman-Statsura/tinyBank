package com.example.tinybank.repository;

import com.example.tinybank.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditJpaRepository extends JpaRepository<Audit,Integer> {
}
