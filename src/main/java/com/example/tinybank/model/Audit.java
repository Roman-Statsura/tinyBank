package com.example.tinybank.model;

import com.example.tinybank.utils.AuditAction;
import com.example.tinybank.utils.ObjectType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "object_id")
    private Integer objectId;
    @Column(name = "object_type")
    @Enumerated(EnumType.STRING)
    private ObjectType objectType;
    @Column(name = "action_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date actionDate;
    @Column(name = "audit_action")
    @Enumerated(EnumType.STRING)
    private AuditAction auditAction;
    @Column(name = "new_value")
    private Double newValue;

}
