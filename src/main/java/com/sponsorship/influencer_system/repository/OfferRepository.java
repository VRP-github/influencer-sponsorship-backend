package com.sponsorship.influencer_system.repository;

import com.sponsorship.influencer_system.model.SponsorshipOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<SponsorshipOffer, Long> {
    Page<SponsorshipOffer> findByInfluencerId(Long influencerId, Pageable pageable);
    Page<SponsorshipOffer> findByBrandId(Long brandId, Pageable pageable);
}
