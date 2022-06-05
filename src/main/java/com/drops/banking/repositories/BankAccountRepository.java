package com.drops.banking.repositories;

import com.drops.banking.entities.Availability;
import com.drops.banking.entities.BankAccount;
import com.drops.banking.entities.Beneficiary;
import com.drops.banking.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findBankAccountByAccountNumber(int accountNumber);
}
