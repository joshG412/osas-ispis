package com.cardmri.osas_ispis.bootstrap;

import com.cardmri.osas_ispis.entity.Role;
import com.cardmri.osas_ispis.entity.UserAccount;
import com.cardmri.osas_ispis.repository.RoleRepository;
import com.cardmri.osas_ispis.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // --- 1. Seed Roles ---
        // If roles are not present in the DB, create them.
        if (roleRepository.count() == 0) {
            List<String> roleNames = Arrays.asList("ROLE_ADMIN", "ROLE_STAFF", "ROLE_STUDENT");
            for (String roleName : roleNames) {
                // We need a constructor in Role to do this cleanly
                // For now, the existing way is fine.
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
            System.out.println("--- Roles have been seeded. ---");
        }

        // --- 2. Seed Admin User ---
        // If no admin user exists, create one.
        if (userAccountRepository.findByRole_Name("ROLE_ADMIN").isEmpty()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: ROLE_ADMIN not found. Make sure it's seeded."));

            UserAccount adminUser = new UserAccount();
            adminUser.setEmail("admin@cardmri.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(adminRole);
            userAccountRepository.save(adminUser);
            System.out.println("--- Default ADMIN user has been created. ---");
        }
        
        // --- 3. Seed Staff User ---
        // If no staff user exists, create one.
        if (userAccountRepository.findByRole_Name("ROLE_STAFF").isEmpty()) {
            Role staffRole = roleRepository.findByName("ROLE_STAFF")
                    .orElseThrow(() -> new RuntimeException("Error: ROLE_STAFF not found. Make sure it's seeded."));

            UserAccount staffUser = new UserAccount();
            staffUser.setEmail("staff@cardmri.com");
            staffUser.setPassword(passwordEncoder.encode("staff123"));
            staffUser.setRole(staffRole);
            userAccountRepository.save(staffUser);
            System.out.println("--- Default STAFF user has been created. ---");
        }
    }
}
