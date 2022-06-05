package com.drops.banking.entities;

import com.drops.banking.enums.ProfileStatus;
import com.drops.banking.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(unique = true,nullable = false)
    private String cin;
    @Column(nullable = false)
    private String firstName ;
    @Column(nullable = false)
    private String lastName ;
    private Date birthday;
    private Sex sex;
    @Column(nullable = false)
    private String address ;
    @Column(nullable = false)
    private String phone ;
    private Date creationDate;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false,unique = true)
    private String username ;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus = ProfileStatus.ACTIVE;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    private Role role;

}
