package com.vechetchuo.Ledgerly.models.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount", nullable = false, precision = 18, scale = 4)
    private BigDecimal amount;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

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

    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_Transaction_Account_AccountId"))
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_Transaction_Category_CategoryId"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_Transaction_GlobalParam_StatusId"))
    private GlobalParam globalParam;
}
