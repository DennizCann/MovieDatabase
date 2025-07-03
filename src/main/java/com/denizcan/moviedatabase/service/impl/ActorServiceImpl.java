package com.denizcan.moviedatabase.service.impl;

import com.denizcan.moviedatabase.model.Actor;
import com.denizcan.moviedatabase.repository.ActorRepository;
import com.denizcan.moviedatabase.service.ActorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }
} 