package com.denizcan.moviedatabase.repository;

import com.denizcan.moviedatabase.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardRepository extends JpaRepository<Award, Long> {
} 