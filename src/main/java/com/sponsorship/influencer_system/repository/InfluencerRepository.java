package com.sponsorship.influencer_system.repository;

import com.sponsorship.influencer_system.model.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
}
