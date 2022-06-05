package com.drops.banking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends  User{

    @ManyToOne(fetch = FetchType.LAZY)
    private Banker banker;
    @ManyToOne(fetch = FetchType.LAZY)
    private Agency agency;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Beneficiary> beneficiaryList;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Meeting> meetingList;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Transfer> transferList;

}
