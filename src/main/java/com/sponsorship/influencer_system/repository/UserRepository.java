package com.sponsorship.influencer_system.repository;

import com.sponsorship.influencer_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    // Allow lookup by email as well (we accept login using email or username)
    Optional<User> findByEmail(String email);
}