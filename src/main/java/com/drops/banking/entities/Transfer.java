package com.drops.banking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorColumn(name="type")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private Date createdAt;
    private Date exuctedAt;
    private String about;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
