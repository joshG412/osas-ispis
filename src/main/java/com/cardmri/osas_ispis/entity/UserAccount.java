package com.cardmri.osas_ispis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_accounts")
public class UserAccount implements UserDetails { // <<--- CHANGE 1: Implement UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;


    // =================================================================
    // METHODS REQUIRED BY UserDetails INTERFACE
    // =================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This converts our single Role enum into a list of authorities that Spring Security can understand.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Our "username" is the user's email address.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // For this project, accounts never expire.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Accounts are never locked.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Passwords never expire.
    }

    @Override
    public boolean isEnabled() {
        return true; // All accounts are enabled.
    }
}
