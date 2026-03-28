package com.example.demo.service;

import com.example.demo.model.PackageReview;
import com.example.demo.model.TravelPackage;
import com.example.demo.model.User;
import com.example.demo.repository.PackageReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PackageReviewService {

    @Autowired
    private PackageReviewRepository reviewRepository;

    @Transactional
    public PackageReview createReview(PackageReview review) {
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<PackageReview> getApprovedReviews() {
        return reviewRepository.findApprovedReviews();
    }

    @Transactional(readOnly = true)
    public List<PackageReview> getApprovedReviewsByPackage(Long packageId) {
        return reviewRepository.findApprovedReviewsByPackageId(packageId);
    }

    @Transactional(readOnly = true)
    public List<PackageReview> getReviewsByUser(Long userId) {
        return reviewRepository.findReviewsByUserId(userId);
    }

    @Transactional
    public void approveReview(Long reviewId) {
        reviewRepository.findById(reviewId).ifPresent(review -> {
            review.setIsApproved(true);
            reviewRepository.save(review);
        });
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

