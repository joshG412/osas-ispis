package com.cardmri.osas_ispis.repository;

import com.cardmri.osas_ispis.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);
    List<UserAccount> findByRole_Name(String roleName);
}
