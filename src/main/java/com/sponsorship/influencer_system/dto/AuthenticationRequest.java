package com.sponsorship.influencer_system.dto;

public class AuthenticationRequest {
    private String username;
    private String email; // accept email field from frontend as well
    private String password;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Convenience: return username if present, otherwise email
    public String getLogin() { return (username != null && !username.isBlank()) ? username : email; }
}