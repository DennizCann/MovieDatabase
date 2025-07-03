package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Director;
import java.util.List;
import java.util.Optional;

public interface DirectorService {
    Director save(Director director);
    Optional<Director> findById(Long id);
    List<Director> findAll();
    void deleteById(Long id);
} 