package com.cardmri.osas_ispis.entity;

import jakarta.persistence.*; // <-- Make sure these imports are correct
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // <-- CRUCIAL: This annotation tells JPA it's a database entity.
@Table(name = "roles") // <-- Good practice to name the table.
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Lombok will generate constructors, getters, and setters.
    // If you don't use Lombok, you must add them manually.
}
