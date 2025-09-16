package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.AuthenticationRequest;
import com.cardmri.osas_ispis.dto.AuthenticationResponse;
import com.cardmri.osas_ispis.dto.RegisterRequest;
import com.cardmri.osas_ispis.entity.UserAccount;
import com.cardmri.osas_ispis.repository.UserAccountRepository;
import com.cardmri.osas_ispis.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new UserAccount();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        user.setRole(request.getRole());

        repository.save(user); // Save the new user to the database

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse login(AuthenticationRequest request) {
        // This line will throw an exception if the credentials are bad.
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // If authentication was successful, find the user and generate a token.
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(); // Should not fail if auth passed.

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
