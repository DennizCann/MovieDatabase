package com.denizcan.moviedatabase.service.impl;

import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.repository.AwardRepository;
import com.denizcan.moviedatabase.service.AwardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AwardServiceImpl implements AwardService {
    private final AwardRepository awardRepository;

    public AwardServiceImpl(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    @Override
    public Award save(Award award) {
        return awardRepository.save(award);
    }

    @Override
    public Optional<Award> findById(Long id) {
        return awardRepository.findById(id);
    }

    @Override
    public List<Award> findAll() {
        return awardRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        awardRepository.deleteById(id);
    }
} 