package com.example.demo.repository;

import com.example.demo.model.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
    List<PlaceImage> findByPlaceId(Long placeId);

    void deleteByPlaceId(Long placeId);
}
