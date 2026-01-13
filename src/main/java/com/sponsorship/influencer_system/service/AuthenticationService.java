package com.sponsorship.influencer_system.service;

import com.sponsorship.influencer_system.dto.AuthenticationRequest;
import com.sponsorship.influencer_system.dto.AuthenticationResponse;
import com.sponsorship.influencer_system.dto.RegisterRequest;
import com.sponsorship.influencer_system.model.Role;
import com.sponsorship.influencer_system.model.User;
import com.sponsorship.influencer_system.repository.UserRepository;
import com.sponsorship.influencer_system.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request, Role role) {
        var user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Use the provided login (username OR email)
        String login = request.getLogin();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        request.getPassword()
                )
        );

        // Try to find user by username first, else by email
        var userOpt = userRepository.findByUsername(login);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(login);
        }
        var user = userOpt.orElseThrow();

        var jwtToken = jwtUtil.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}