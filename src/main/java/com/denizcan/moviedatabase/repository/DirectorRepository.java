package com.denizcan.moviedatabase.repository;

import com.denizcan.moviedatabase.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
} 