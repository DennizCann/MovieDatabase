package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Award;
import java.util.List;
import java.util.Optional;

public interface AwardService {
    Award save(Award award);
    Optional<Award> findById(Long id);
    List<Award> findAll();
    void deleteById(Long id);
} 