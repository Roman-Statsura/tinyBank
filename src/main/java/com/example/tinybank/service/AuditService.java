package com.example.tinybank.service;

import com.example.tinybank.model.Audit;

import java.util.List;

public interface AuditService {
    Audit findById(Integer id);
    List<Audit> findAll();
    void saveAudit(Audit audit);
}
