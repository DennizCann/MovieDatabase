package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Actor;
import java.util.List;
import java.util.Optional;

public interface ActorService {
    Actor save(Actor actor);
    Optional<Actor> findById(Long id);
    List<Actor> findAll();
    void deleteById(Long id);
} 