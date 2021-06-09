package com.example.tinybank.service.impl;

import com.example.tinybank.model.Audit;
import com.example.tinybank.repository.AuditJpaRepository;
import com.example.tinybank.service.AuditService;
import com.example.tinybank.utils.AuditAction;
import com.example.tinybank.utils.ObjectType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AuditServiceImpl implements AuditService {
    private final AuditJpaRepository auditJpaRepository;

    public AuditServiceImpl(AuditJpaRepository auditJpaRepository) {
        this.auditJpaRepository = auditJpaRepository;
    }
    public Audit findById(Integer id){
        return auditJpaRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find audit by ID - "+id));
    }
    public List<Audit> findAll(){
        return auditJpaRepository
                .findAll();
    }
    public void saveAudit(Audit audit){
        if (audit!=null)
            auditJpaRepository
                    .save(audit);
    }
    public Audit createAudit(Integer id, ObjectType objectType, Date date, AuditAction auditAction,
                             Double newValue){
        return Audit.builder()
                .objectId(id)
                .objectType(objectType)
                .actionDate(date)
                .auditAction(auditAction)
                .newValue(newValue)
                .build();
    }
}
