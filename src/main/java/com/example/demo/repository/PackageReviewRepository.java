package com.example.demo.repository;

import com.example.demo.model.PackageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PackageReviewRepository extends JpaRepository<PackageReview, Long> {
    
    @Query("SELECT r FROM PackageReview r WHERE r.isApproved = true ORDER BY r.createdAt DESC")
    List<PackageReview> findApprovedReviews();
    
    @Query("SELECT r FROM PackageReview r JOIN FETCH r.user WHERE r.travelPackage.id = :packageId AND r.isApproved = true ORDER BY r.createdAt DESC")
    List<PackageReview> findApprovedReviewsByPackageId(Long packageId);
    
    @Query("SELECT r FROM PackageReview r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    List<PackageReview> findReviewsByUserId(Long userId);
    
    @Query("SELECT r FROM PackageReview r WHERE r.travelPackage.id = :packageId")
    List<PackageReview> findAllByTravelPackageId(Long packageId);
}

