package com.example.tinybank.service;

import com.example.tinybank.model.Audit;
import com.example.tinybank.utils.AuditAction;
import com.example.tinybank.utils.ObjectType;

import java.util.Date;
import java.util.List;

public interface AuditService {
    Audit findById(Integer id);
    List<Audit> findAll();
    void saveAudit(Audit audit);
    Audit createAudit(Integer id, ObjectType objectType, Date date, AuditAction auditAction,Double newValue);
    List<Audit> findAuditsByObjectId(Integer id);
}
