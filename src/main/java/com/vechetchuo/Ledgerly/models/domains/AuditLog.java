package com.vechetchuo.Ledgerly.models.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Entity
@Table(name = "audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "controller_name", nullable = false, length = 100)
    private String controllerName;

    @Column(name = "method_name", nullable = false, length = 100)
    private String methodName;

    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

    @Column(name = "transaction_no", nullable = false, length = 100)
    private String transactionNo;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String Description;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
