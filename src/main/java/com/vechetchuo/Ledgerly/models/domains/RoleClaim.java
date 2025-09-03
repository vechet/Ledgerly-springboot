package com.vechetchuo.Ledgerly.models.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String claimType;   // e.g., "Permission"
    private String claimValue;  // e.g., "CAN_EDIT_USER"

    public RoleClaim(Role role, String claimType, String claimValue) {
        this.role = role;
        this.claimType = claimType;
        this.claimValue = claimValue;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
