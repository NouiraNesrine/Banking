package com.drops.banking.repositories;

import com.drops.banking.entities.Agency;
import com.drops.banking.entities.Availability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.Date;


public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

}
