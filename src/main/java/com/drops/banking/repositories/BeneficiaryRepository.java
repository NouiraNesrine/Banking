package com.drops.banking.repositories;

import com.drops.banking.entities.Banker;
import com.drops.banking.entities.Beneficiary;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    Optional<Beneficiary> findBeneficiaryByAccountNumber(int accountNumber);
}
