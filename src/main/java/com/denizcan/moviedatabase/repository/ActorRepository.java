package com.denizcan.moviedatabase.repository;

import com.denizcan.moviedatabase.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
} 