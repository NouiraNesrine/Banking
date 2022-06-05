package com.drops.banking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String role;
    @JsonIgnore
    @OneToMany(mappedBy = "role",fetch= FetchType.LAZY)
    private List<Customer> customerList;
    @JsonIgnore
    @OneToMany(mappedBy = "role",fetch=FetchType.LAZY)
    private List<Banker> bankerList;
    @JsonIgnore
    @OneToMany(mappedBy = "role",fetch=FetchType.LAZY)
    private List<Supervisor> supervisorList;
}
