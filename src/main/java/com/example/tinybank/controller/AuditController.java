package com.example.tinybank.controller;

import com.example.tinybank.service.impl.AuditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class AuditController {
    private final AuditServiceImpl auditService;
    @Autowired
    public AuditController(AuditServiceImpl auditService) {
        this.auditService = auditService;
    }
    @GetMapping("/log-list")
    public String findAll(Model model){
        var logs = auditService.findAll();
        model.addAttribute("logs",logs);
        return "log-list";
    }
    @GetMapping("/log-list/{id}")
    public String findAllLogsById(@PathVariable("id") Integer id,Model model){
        var logs = auditService.findAuditsByObjectId(id);
        model.addAttribute("logs",logs);
        return "log-list";
    }
}
