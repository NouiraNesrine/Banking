package com.drops.banking.repositories;

import com.drops.banking.entities.AccountOperation;
import com.drops.banking.entities.Agency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Optional<Agency> findByName(String name);
}
