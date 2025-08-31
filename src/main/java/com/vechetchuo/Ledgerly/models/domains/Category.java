package com.vechetchuo.Ledgerly.models.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "icon_name", length = 100)
    private String iconName;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "is_system_value", nullable = false)
    private boolean isSystemValue;

    @Column(name = "memo", length = 500)
    private String memo;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    // Self-referencing many-to-one relationship
    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_Category_Parent_ParentId"))
    private Category parent;

    // Self-referencing one-to-many relationship
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> children;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_Category_GlobalParam_StatusId"))
    private GlobalParam globalParam;
}
