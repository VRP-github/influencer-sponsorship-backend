package com.sponsorship.influencer_system.service;

import com.sponsorship.influencer_system.dto.CreateOfferRequest;
import com.sponsorship.influencer_system.dto.UpdateOfferStatusRequest;
import com.sponsorship.influencer_system.model.Brand;
import com.sponsorship.influencer_system.model.Influencer;
import com.sponsorship.influencer_system.model.OfferStatus;
import com.sponsorship.influencer_system.model.SponsorshipOffer;
import com.sponsorship.influencer_system.repository.BrandRepository;
import com.sponsorship.influencer_system.repository.InfluencerRepository;
import com.sponsorship.influencer_system.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final BrandRepository brandRepository;
    private final InfluencerRepository influencerRepository;

    public OfferService(OfferRepository offerRepository, BrandRepository brandRepository, InfluencerRepository influencerRepository) {
        this.offerRepository = offerRepository;
        this.brandRepository = brandRepository;
        this.influencerRepository = influencerRepository;
    }

    @Transactional
    public SponsorshipOffer createOffer(CreateOfferRequest request) {
        // Validate brand exists
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + request.getBrandId()));

        // Validate influencer exists
        Influencer influencer = influencerRepository.findById(request.getInfluencerId())
                .orElseThrow(() -> new RuntimeException("Influencer not found with id: " + request.getInfluencerId()));

        // Check if brand has enough budget
        if (brand.getRemainingBudget().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient budget. Brand has " + brand.getRemainingBudget() + " but offer amount is " + request.getAmount());
        }

        // Create the offer
        SponsorshipOffer offer = new SponsorshipOffer();
        offer.setBrand(brand);
        offer.setInfluencer(influencer);
        offer.setAmount(request.getAmount());
        offer.setStatus(OfferStatus.PENDING);
        offer.setCreatedAt(Instant.now());

        return offerRepository.save(offer);
    }

    @Transactional
    public SponsorshipOffer updateOfferStatus(Long id, UpdateOfferStatusRequest request) {
        SponsorshipOffer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));

        // Validate status can only be updated from PENDING
        if (offer.getStatus() != OfferStatus.PENDING) {
            throw new RuntimeException("Offer status can only be updated when it's PENDING. Current status: " + offer.getStatus());
        }

        // Parse and validate status
        OfferStatus newStatus;
        try {
            newStatus = OfferStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + request.getStatus() + ". Must be ACCEPTED or REJECTED.");
        }

        // Only allow ACCEPTED or REJECTED
        if (newStatus != OfferStatus.ACCEPTED && newStatus != OfferStatus.REJECTED) {
            throw new RuntimeException("Status can only be updated to ACCEPTED or REJECTED");
        }

        offer.setStatus(newStatus);

        // If accepted, update budgets and earnings
        if (newStatus == OfferStatus.ACCEPTED) {
            Brand brand = offer.getBrand();
            Influencer influencer = offer.getInfluencer();
            BigDecimal amount = offer.getAmount();

            // Deduct from brand budget
            brand.setRemainingBudget(brand.getRemainingBudget().subtract(amount));
            brandRepository.save(brand);

            // Add to influencer earnings
            influencer.setTotalEarnings(influencer.getTotalEarnings().add(amount));
            influencerRepository.save(influencer);
        }

        return offerRepository.save(offer);
    }
}
