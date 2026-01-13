package com.sponsorship.influencer_system.controller;

import com.sponsorship.influencer_system.dto.AuthenticationRequest;
import com.sponsorship.influencer_system.dto.AuthenticationResponse;
import com.sponsorship.influencer_system.dto.RegisterRequest;
import com.sponsorship.influencer_system.model.Role;
import com.sponsorship.influencer_system.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    // Endpoint for Influencers to Sign Up
    @PostMapping("/register/influencer")
    public ResponseEntity<AuthenticationResponse> registerInfluencer(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request, Role.INFLUENCER));
    }

    // Endpoint for Brands to Sign Up
    @PostMapping("/register/brand")
    public ResponseEntity<AuthenticationResponse> registerBrand(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request, Role.BRAND));
    }

    // Standard Login for everyone
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}