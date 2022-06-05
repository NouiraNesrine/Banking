package com.drops.banking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Banker extends User{

    @ManyToOne
    private Agency agency;
    @ManyToOne
    private Supervisor supervisor;
    @OneToMany(mappedBy = "banker",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Customer> customerList;
    @OneToMany(mappedBy = "banker", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Availability> availabilityList;
    @OneToMany(mappedBy ="banker")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Meeting> meetingList;




}
