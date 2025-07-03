package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.service.MovieService;
import com.denizcan.moviedatabase.dto.MovieDTO;
import com.denizcan.moviedatabase.mapper.MovieMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@Tag(name = "Film Yönetimi", description = "Film CRUD işlemleri için API endpoint'leri")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(summary = "Tüm filmleri getir", description = "Veritabanındaki tüm filmleri listeler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filmler başarıyla getirildi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = MovieDTO.class)))
    })
    public List<MovieDTO> getAllMovies() {
        return movieService.findAll()
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID ile film getir", description = "Belirtilen ID'ye sahip filmi getirir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Film başarıyla getirildi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = MovieDTO.class))),
        @ApiResponse(responseCode = "404", description = "Film bulunamadı")
    })
    public ResponseEntity<MovieDTO> getMovieById(
            @Parameter(description = "Film ID'si", required = true) @PathVariable Long id) {
        Optional<Movie> movie = movieService.findById(id);
        return movie.map(m -> ResponseEntity.ok(MovieMapper.toDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Yeni film oluştur", description = "Yeni bir film kaydı oluşturur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Film başarıyla oluşturuldu",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = MovieDTO.class))),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<?> createMovie(
            @Parameter(description = "Oluşturulacak film bilgileri", required = true) 
            @RequestBody MovieDTO movieDTO) {
        try {
            Movie movie = MovieMapper.toEntity(movieDTO);
            Movie savedMovie = movieService.save(movie);
            return ResponseEntity.ok(MovieMapper.toDTO(savedMovie));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("Hata: " + e.getMessage() + "\nStack Trace: " + e.getStackTrace()[0]);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Film güncelle", description = "Belirtilen ID'ye sahip filmi günceller")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Film başarıyla güncellendi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = MovieDTO.class))),
        @ApiResponse(responseCode = "404", description = "Film bulunamadı")
    })
    public ResponseEntity<MovieDTO> updateMovie(
            @Parameter(description = "Film ID'si", required = true) @PathVariable Long id,
            @Parameter(description = "Güncellenecek film bilgileri", required = true) @RequestBody MovieDTO movieDTO) {
        if (!movieService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Movie movie = MovieMapper.toEntity(movieDTO);
        movie.setId(id);
        Movie savedMovie = movieService.save(movie);
        return ResponseEntity.ok(MovieMapper.toDTO(savedMovie));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Film sil", description = "Belirtilen ID'ye sahip filmi siler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Film başarıyla silindi"),
        @ApiResponse(responseCode = "404", description = "Film bulunamadı")
    })
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "Film ID'si", required = true) @PathVariable Long id) {
        if (!movieService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 