package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Actor;
import com.denizcan.moviedatabase.service.ActorService;
import com.denizcan.moviedatabase.dto.ActorDTO;
import com.denizcan.moviedatabase.mapper.ActorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.findAll()
                .stream()
                .map(ActorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        Optional<Actor> actor = actorService.findById(id);
        return actor.map(a -> ResponseEntity.ok(ActorMapper.toDTO(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Actor createActor(@RequestBody Actor actor) {
        return actorService.save(actor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        if (!actorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        actor.setId(id);
        return ResponseEntity.ok(actorService.save(actor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        if (!actorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 