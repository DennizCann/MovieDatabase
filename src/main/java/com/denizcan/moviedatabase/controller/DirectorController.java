package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Director;
import com.denizcan.moviedatabase.service.DirectorService;
import com.denizcan.moviedatabase.dto.DirectorDTO;
import com.denizcan.moviedatabase.mapper.DirectorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<DirectorDTO> getAllDirectors() {
        return directorService.findAll()
                .stream()
                .map(DirectorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable Long id) {
        Optional<Director> director = directorService.findById(id);
        return director.map(d -> ResponseEntity.ok(DirectorMapper.toDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Director createDirector(@RequestBody Director director) {
        return directorService.save(director);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable Long id, @RequestBody Director director) {
        if (!directorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        director.setId(id);
        return ResponseEntity.ok(directorService.save(director));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        if (!directorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        directorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 