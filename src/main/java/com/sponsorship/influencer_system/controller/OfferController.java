package com.sponsorship.influencer_system.controller;

import com.sponsorship.influencer_system.dto.CreateOfferRequest;
import com.sponsorship.influencer_system.dto.UpdateOfferStatusRequest;
import com.sponsorship.influencer_system.model.SponsorshipOffer;
import com.sponsorship.influencer_system.repository.OfferRepository;
import com.sponsorship.influencer_system.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
public class OfferController {
    
    private final OfferService offerService;
    private final OfferRepository offerRepository;

    public OfferController(OfferService offerService, OfferRepository offerRepository) {
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }

    /**
     * POST /offers - Create a new offer
     * Example body:
     * {
     *   "brandId": 1,
     *   "influencerId": 1,
     *   "amount": 5000.00
     * }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SponsorshipOffer createOffer(@RequestBody CreateOfferRequest request) {
        return offerService.createOffer(request);
    }

    /**
     * PATCH /offers/{id} - Accept or reject an offer
     * Example body for accepting: { "status": "ACCEPTED" }
     * Example body for rejecting: { "status": "REJECTED" }
     */
    @PatchMapping("/{id}")
    public SponsorshipOffer updateOfferStatus(@PathVariable Long id,
                                              @RequestBody UpdateOfferStatusRequest request) {
        return offerService.updateOfferStatus(id, request);
    }

    /**
     * GET /offers?influencerId=1 - Get offers for a specific influencer
     * GET /offers?brandId=1 - Get offers from a specific brand
     * GET /offers - Get all offers
     * 
     * All support pagination and sorting:
     * ?page=0&size=10&sort=createdAt,desc
     */
    @GetMapping
    public Page<SponsorshipOffer> getOffers(@RequestParam(required = false) Long influencerId,
                                            @RequestParam(required = false) Long brandId,
                                            Pageable pageable) {
        if (influencerId != null) {
            return offerRepository.findByInfluencerId(influencerId, pageable);
        } else if (brandId != null) {
            return offerRepository.findByBrandId(brandId, pageable);
        } else {
            return offerRepository.findAll(pageable);
        }
    }

    /**
     * GET /offers/{id} - Get specific offer by ID
     */
    @GetMapping("/{id}")
    public SponsorshipOffer getOfferById(@PathVariable Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
    }
}
