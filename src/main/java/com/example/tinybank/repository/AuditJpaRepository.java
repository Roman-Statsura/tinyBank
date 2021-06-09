package com.example.tinybank.repository;

import com.example.tinybank.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditJpaRepository extends JpaRepository<Audit,Integer> {
    List<Audit> findAuditsByObjectId(Integer id);
}
