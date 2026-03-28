package com.example.demo.repository;

import com.example.demo.model.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {
    void deleteByHotelId(Long hotelId);
}

