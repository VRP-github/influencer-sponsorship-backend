package com.sponsorship.influencer_system.controller;

import com.sponsorship.influencer_system.model.Influencer;
import com.sponsorship.influencer_system.repository.InfluencerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/influencers")
public class InfluencerController {
    
    private final InfluencerRepository influencerRepository;

    public InfluencerController(InfluencerRepository influencerRepository) {
        this.influencerRepository = influencerRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Influencer createInfluencer(@RequestBody Influencer influencer) {
        influencer.setId(null); // Ensure it's a new entity
        influencer.setTotalEarnings(BigDecimal.ZERO); // Initialize earnings
        return influencerRepository.save(influencer);
    }

    @GetMapping
    public Page<Influencer> getAllInfluencers(Pageable pageable) {
        return influencerRepository.findAll(pageable);
    }
    @GetMapping("/{id}")
    public Influencer getInfluencerById(@PathVariable Long id) {
        return influencerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Influencer not found with id: " + id));
    }
}
