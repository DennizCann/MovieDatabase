package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.service.AwardService;
import com.denizcan.moviedatabase.dto.AwardDTO;
import com.denizcan.moviedatabase.mapper.AwardMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/awards")
public class AwardController {
    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping
    public List<AwardDTO> getAllAwards() {
        return awardService.findAll()
                .stream()
                .map(AwardMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AwardDTO> getAwardById(@PathVariable Long id) {
        Optional<Award> award = awardService.findById(id);
        return award.map(a -> ResponseEntity.ok(AwardMapper.toDTO(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Award createAward(@RequestBody Award award) {
        return awardService.save(award);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Award> updateAward(@PathVariable Long id, @RequestBody Award award) {
        if (!awardService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        award.setId(id);
        return ResponseEntity.ok(awardService.save(award));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAward(@PathVariable Long id) {
        if (!awardService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        awardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 