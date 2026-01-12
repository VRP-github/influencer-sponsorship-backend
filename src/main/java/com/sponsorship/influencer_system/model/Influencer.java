package com.sponsorship.influencer_system.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Influencer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String platform; 
    private Long followers;
    private Double engagementRate; 

    @Column(precision = 19, scale = 2)
    private BigDecimal totalEarnings = BigDecimal.ZERO;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    public Double getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(Double engagementRate) {
        this.engagementRate = engagementRate;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}
