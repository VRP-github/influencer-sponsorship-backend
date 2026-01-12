package com.sponsorship.influencer_system.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalBudget;

    @Column(precision = 19, scale = 2)
    private BigDecimal remainingBudget;

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

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(BigDecimal remainingBudget) {
        this.remainingBudget = remainingBudget;
    }
}
