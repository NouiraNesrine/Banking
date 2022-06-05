package com.drops.banking.repositories;

import com.drops.banking.entities.Customer;
import com.drops.banking.entities.Meeting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
