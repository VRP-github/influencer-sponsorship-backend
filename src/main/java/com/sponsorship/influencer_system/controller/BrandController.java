package com.sponsorship.influencer_system.controller;

import com.sponsorship.influencer_system.model.Brand;
import com.sponsorship.influencer_system.repository.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
public class BrandController {
    
    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * POST /brands - Create a new brand
     * Example request body:
     * {
     *   "name": "Nike",
     *   "totalBudget": 100000.00
     * }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brand createBrand(@RequestBody Brand brand) {
        brand.setId(null); // Ensure it's a new entity
        brand.setRemainingBudget(brand.getTotalBudget()); // Initialize remaining budget
        return brandRepository.save(brand);
    }

    /**
     * GET /brands - Get all brands with pagination and sorting
     * Examples:
     *   GET /brands
     *   GET /brands?page=0&size=10
     *   GET /brands?page=0&size=10&sort=name,asc
     *   GET /brands?sort=remainingBudget,desc
     */
    @GetMapping
    public Page<Brand> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    /**
     * GET /brands/{id} - Get a specific brand by ID
     * Example: GET /brands/1
     */
    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
    }
}
