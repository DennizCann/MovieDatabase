package com.denizcan.moviedatabase.repository;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    // Başlığa göre arama (case-insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);
    
    // Yıla göre arama
    List<Movie> findByYear(Integer year);
    
    // Yıl aralığına göre arama
    List<Movie> findByYearBetween(Integer startYear, Integer endYear);
    
    // IMDB puanına göre arama
    List<Movie> findByImdbRatingGreaterThanEqual(Double rating);
    
    // Seri filmlerini getir
    List<Movie> findByPartOfSeriesTrue();
    
    // Belirli bir serinin filmlerini getir
    List<Movie> findBySeriesName(String seriesName);
    
    // Tür'e göre arama (JPQL ile)
    @Query("SELECT DISTINCT m FROM Movie m JOIN m.genre g WHERE g = :genre")
    List<Movie> findByGenre(@Param("genre") MovieGenre genre);
    
    // Gelişmiş arama (başlık, yıl, minimum puan)
    @Query("SELECT m FROM Movie m WHERE " +
           "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:year IS NULL OR m.year = :year) AND " +
           "(:minRating IS NULL OR m.imdbRating >= :minRating)")
    Page<Movie> findMoviesWithFilters(
            @Param("title") String title,
            @Param("year") Integer year,
            @Param("minRating") Double minRating,
            Pageable pageable);
    
    // En yüksek puanlı filmler
    @Query("SELECT m FROM Movie m ORDER BY m.imdbRating DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);
    
    // En yeni filmler
    @Query("SELECT m FROM Movie m ORDER BY m.year DESC")
    Page<Movie> findLatestMovies(Pageable pageable);
} 