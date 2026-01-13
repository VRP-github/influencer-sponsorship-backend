package com.sponsorship.influencer_system.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_user") // We use _user because "user" is a reserved word in Postgres
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String username; // This exists in DB, but we will use email for login

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // --- Constructors ---
    public User() {}

    public User(String firstname, String lastname, String username, String email, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // --- Builder Pattern Support (Optional, but useful if you use .builder() in Service) ---
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String firstname;
        private String lastname;
        private String username;
        private String email;
        private String password;
        private Role role;

        UserBuilder() {}

        public UserBuilder firstname(String firstname) { this.firstname = firstname; return this; }
        public UserBuilder lastname(String lastname) { this.lastname = lastname; return this; }
        public UserBuilder username(String username) { this.username = username; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder password(String password) { this.password = password; return this; }
        public UserBuilder role(Role role) { this.role = role; return this; }

        public User build() {
            return new User(firstname, lastname, username, email, password, role);
        }
    }

    // --- UserDetails Methods (THE CRITICAL SECURITY FIXES) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // FIX 1: Return EMAIL here. Spring Security uses this to identify the user.
        // Since your form sends 'email', this must match.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // FIX 2: Must be TRUE, or you can't log in
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // FIX 3: Must be TRUE
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // FIX 4: Must be TRUE
        return true;
    }

    @Override
    public boolean isEnabled() {
        // FIX 5: Must be TRUE
        return true;
    }

    // --- Getters and Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public void setUsername(String username) { this.username = username; }
}