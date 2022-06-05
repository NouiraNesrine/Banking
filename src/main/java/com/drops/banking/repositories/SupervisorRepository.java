package com.drops.banking.repositories;

import com.drops.banking.entities.SavingAccount;
import com.drops.banking.entities.Supervisor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;


public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    Optional<Supervisor> findSupervisorByUsername(String username);
    Optional<Supervisor> findByCin(String cin);

}
