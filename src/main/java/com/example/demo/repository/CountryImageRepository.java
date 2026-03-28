package com.example.demo.repository;

import com.example.demo.model.CountryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryImageRepository extends JpaRepository<CountryImage, Long> {
    List<CountryImage> findByCountryId(Long countryId);

    void deleteByCountryId(Long countryId);
}
