package com.cardmri.osas_ispis.repository;

import com.cardmri.osas_ispis.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // This method allows us to find a Role by its name
    Optional<Role> findByName(String name);
}
