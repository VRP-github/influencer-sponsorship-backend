package com.sponsorship.influencer_system.dto;

import java.math.BigDecimal;

public class CreateOfferRequest {
    private Long brandId;
    private Long influencerId;
    private BigDecimal amount;

    // Getters and setters
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(Long influencerId) {
        this.influencerId = influencerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
