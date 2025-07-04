package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;
import com.denizcan.moviedatabase.service.MovieService;
import com.denizcan.moviedatabase.dto.MovieDTO;
import com.denizcan.moviedatabase.mapper.MovieMapper;
import com.denizcan.moviedatabase.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        Movie movie = movieService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film", "id", id));
        return ResponseEntity.ok(MovieMapper.toDTO(movie));
    }

    @PostMapping
    @Operation(summary = "Yeni film oluştur", description = "Yeni bir film kaydı oluşturur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Film başarıyla oluşturuldu",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = MovieDTO.class))),
        @ApiResponse(responseCode = "400", description = "Geçersiz veri"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<MovieDTO> createMovie(
            @Parameter(description = "Oluşturulacak film bilgileri", required = true) 
            @Valid @RequestBody MovieDTO movieDTO) {
        Movie movie = MovieMapper.toEntity(movieDTO);
        Movie savedMovie = movieService.save(movie);
        return ResponseEntity.ok(MovieMapper.toDTO(savedMovie));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Film güncelle", description = "Belirtilen ID'ye sahip filmi günceller")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Film başarıyla güncellendi",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = MovieDTO.class))),
        @ApiResponse(responseCode = "400", description = "Geçersiz veri"),
        @ApiResponse(responseCode = "404", description = "Film bulunamadı")
    })
    public ResponseEntity<MovieDTO> updateMovie(
            @Parameter(description = "Film ID'si", required = true) @PathVariable Long id,
            @Parameter(description = "Güncellenecek film bilgileri", required = true) 
            @Valid @RequestBody MovieDTO movieDTO) {
        if (!movieService.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Film", "id", id);
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
            throw new ResourceNotFoundException("Film", "id", id);
        }
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Arama ve filtreleme endpoint'leri
    @GetMapping("/search")
    @Operation(summary = "Film arama", description = "Başlığa göre film arama")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arama sonuçları başarıyla getirildi")
    })
    public List<MovieDTO> searchMovies(
            @Parameter(description = "Aranacak film başlığı") @RequestParam String title) {
        return movieService.findByTitle(title)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/year/{year}")
    @Operation(summary = "Yıla göre filmler", description = "Belirli bir yılda yayınlanan filmleri getirir")
    public List<MovieDTO> getMoviesByYear(
            @Parameter(description = "Yayın yılı") @PathVariable Integer year) {
        return movieService.findByYear(year)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/year-range")
    @Operation(summary = "Yıl aralığına göre filmler", description = "Belirli yıl aralığında yayınlanan filmleri getirir")
    public List<MovieDTO> getMoviesByYearRange(
            @Parameter(description = "Başlangıç yılı") @RequestParam Integer startYear,
            @Parameter(description = "Bitiş yılı") @RequestParam Integer endYear) {
        return movieService.findByYearRange(startYear, endYear)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rating/{minRating}")
    @Operation(summary = "Minimum puana göre filmler", description = "Belirli minimum IMDB puanına sahip filmleri getirir")
    public List<MovieDTO> getMoviesByMinRating(
            @Parameter(description = "Minimum IMDB puanı") @PathVariable Double minRating) {
        return movieService.findByMinRating(minRating)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/series")
    @Operation(summary = "Seri filmler", description = "Tüm seri filmleri getirir")
    public List<MovieDTO> getSeriesMovies() {
        return movieService.findSeriesMovies()
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/series/{seriesName}")
    @Operation(summary = "Seri filmleri", description = "Belirli bir serinin filmlerini getirir")
    public List<MovieDTO> getMoviesBySeries(
            @Parameter(description = "Seri adı") @PathVariable String seriesName) {
        return movieService.findBySeriesName(seriesName)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Türe göre filmler", description = "Belirli türdeki filmleri getirir")
    public List<MovieDTO> getMoviesByGenre(
            @Parameter(description = "Film türü") @PathVariable MovieGenre genre) {
        return movieService.findByGenre(genre)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    @Operation(summary = "Gelişmiş filtreleme", description = "Çoklu kriterlere göre film filtreleme")
    public Page<MovieDTO> filterMovies(
            @Parameter(description = "Film başlığı (opsiyonel)") @RequestParam(required = false) String title,
            @Parameter(description = "Yayın yılı (opsiyonel)") @RequestParam(required = false) Integer year,
            @Parameter(description = "Minimum IMDB puanı (opsiyonel)") @RequestParam(required = false) Double minRating,
            @Parameter(description = "Sayfa numarası") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Sayfa boyutu") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sıralama alanı") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sıralama yönü") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Movie> movies = movieService.findMoviesWithFilters(title, year, minRating, pageable);
        return movies.map(MovieMapper::toDTO);
    }

    @GetMapping("/top-rated")
    @Operation(summary = "En yüksek puanlı filmler", description = "IMDB puanına göre en yüksek puanlı filmleri getirir")
    public Page<MovieDTO> getTopRatedMovies(
            @Parameter(description = "Sayfa numarası") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Sayfa boyutu") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("imdbRating").descending());
        Page<Movie> movies = movieService.findTopRatedMovies(pageable);
        return movies.map(MovieMapper::toDTO);
    }

    @GetMapping("/latest")
    @Operation(summary = "En yeni filmler", description = "Yayın yılına göre en yeni filmleri getirir")
    public Page<MovieDTO> getLatestMovies(
            @Parameter(description = "Sayfa numarası") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Sayfa boyutu") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("year").descending());
        Page<Movie> movies = movieService.findLatestMovies(pageable);
        return movies.map(MovieMapper::toDTO);
    }
} 