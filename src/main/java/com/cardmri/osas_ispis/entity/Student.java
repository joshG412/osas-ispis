package com.cardmri.osas_ispis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String yearLevel;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY is good here, we don't always need the full Program object.
    @JoinColumn(name = "program_id")   // This will create a 'program_id' foreign key column in the 'students' table.
    private Program program;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    private UserAccount userAccount;
}
