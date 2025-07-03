package com.denizcan.moviedatabase.repository;

import com.denizcan.moviedatabase.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
} 