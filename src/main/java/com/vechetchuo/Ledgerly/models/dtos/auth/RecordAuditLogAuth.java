package com.vechetchuo.Ledgerly.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordAuditLogAuth {
    private String id;
    private String username;
    private boolean enabled;
    private String phone;
    private String email;
    private String address;
//    private String memo;
//    private int statusId;
//    private String statusName;
//    private String createdBy;
//    private LocalDateTime createdDate;
//    private String modifiedBy;
//    private LocalDateTime modifiedDate;
}
