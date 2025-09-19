package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.AuthenticationRequest;
import com.cardmri.osas_ispis.dto.AuthenticationResponse;
import com.cardmri.osas_ispis.dto.RegisterRequest;
import com.cardmri.osas_ispis.entity.Program;
import com.cardmri.osas_ispis.entity.Role;
import com.cardmri.osas_ispis.entity.Student;
import com.cardmri.osas_ispis.entity.UserAccount;
import com.cardmri.osas_ispis.repository.ProgramRepository;
import com.cardmri.osas_ispis.repository.RoleRepository;
import com.cardmri.osas_ispis.repository.StudentRepository;
import com.cardmri.osas_ispis.repository.UserAccountRepository;
import com.cardmri.osas_ispis.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserAccountRepository repository; // Kept original name 'repository'
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProgramRepository programRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // 1. Find the "ROLE_STUDENT" Role entity from the database
        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new IllegalStateException("Error: ROLE_STUDENT is not found in the database."));

        // 2. Create the UserAccount
        var userAccount = new UserAccount();
        userAccount.setEmail(request.getEmail());
        userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        userAccount.setRole(studentRole); // Assign the Role object
        var savedUser = repository.save(userAccount);

        // 3. Create the associated Student profile with all details
        var student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setUserAccount(savedUser);

        // --- Logic from the updated code to set additional fields ---
        student.setPhoneNumber(request.getPhoneNumber());
        student.setYearLevel(request.getYearLevel());
        if (request.getProgramId() != null) {
            Program program = programRepository.findById(request.getProgramId())
                    .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + request.getProgramId()));
            student.setProgram(program);
        }
        // --- End of updated logic ---

        studentRepository.save(student);

        // 4. Generate and return the JWT token
        var jwtToken = jwtService.generateToken(savedUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + request.getEmail()));
        
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
