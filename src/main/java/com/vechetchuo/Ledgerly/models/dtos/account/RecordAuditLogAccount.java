package com.vechetchuo.Ledgerly.models.dtos.account;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordAuditLogAccount {
    private int id;
    private String name;
    private String currency;
    private String memo;
    private int statusId;
    private String statusName;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
}
