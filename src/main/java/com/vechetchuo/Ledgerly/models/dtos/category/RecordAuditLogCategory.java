package com.vechetchuo.Ledgerly.models.dtos.category;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordAuditLogCategory {
    private int id;
    private String name;
    private int parentId;
    private String parentName;
    private String memo;
    private boolean defaultCategory;
    private int statusId;
    private String statusName;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
}
