package com.vechetchuo.Ledgerly.models.domains;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Table(name = "global_param")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "key_name", nullable = false, length = 100)
    private String keyName;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "memo", length = 500)
    private String memo;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "globalParam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "globalParam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "globalParam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
