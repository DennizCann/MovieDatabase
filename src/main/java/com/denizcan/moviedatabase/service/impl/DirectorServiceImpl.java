package com.denizcan.moviedatabase.service.impl;

import com.denizcan.moviedatabase.model.Director;
import com.denizcan.moviedatabase.repository.DirectorRepository;
import com.denizcan.moviedatabase.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public Director save(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Optional<Director> findById(Long id) {
        return directorRepository.findById(id);
    }

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        directorRepository.deleteById(id);
    }
} 