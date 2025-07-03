package com.denizcan.moviedatabase;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.Actor;
import com.denizcan.moviedatabase.model.Director;
import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.repository.MovieRepository;
import com.denizcan.moviedatabase.repository.ActorRepository;
import com.denizcan.moviedatabase.repository.DirectorRepository;
import com.denizcan.moviedatabase.repository.AwardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataLoadTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Test
    void testDataLoad() {
        // Filmleri kontrol et
        List<Movie> movies = movieRepository.findAll();
        assertFalse(movies.isEmpty(), "Filmler yüklenmemiş");
        assertEquals(5, movies.size(), "Beklenen film sayısı 5 olmalı");

        // İlk filmi kontrol et
        Optional<Movie> inception = movieRepository.findById(1L);
        assertTrue(inception.isPresent(), "Inception filmi bulunamadı");
        assertEquals("Inception", inception.get().getTitle(), "Film adı yanlış");

        // Oyuncuları kontrol et
        List<Actor> actors = actorRepository.findAll();
        assertFalse(actors.isEmpty(), "Oyuncular yüklenmemiş");
        assertEquals(8, actors.size(), "Beklenen oyuncu sayısı 8 olmalı");

        // Yönetmenleri kontrol et
        List<Director> directors = directorRepository.findAll();
        assertFalse(directors.isEmpty(), "Yönetmenler yüklenmemiş");
        assertEquals(5, directors.size(), "Beklenen yönetmen sayısı 5 olmalı");

        // Ödülleri kontrol et
        List<Award> awards = awardRepository.findAll();
        assertFalse(awards.isEmpty(), "Ödüller yüklenmemiş");
        assertEquals(8, awards.size(), "Beklenen ödül sayısı 8 olmalı");

        System.out.println("✅ Test verileri başarıyla yüklendi!");
        System.out.println("📽️ Film sayısı: " + movies.size());
        System.out.println("🎭 Oyuncu sayısı: " + actors.size());
        System.out.println("🎬 Yönetmen sayısı: " + directors.size());
        System.out.println("🏆 Ödül sayısı: " + awards.size());
    }

    @Test
    void testMovieDetails() {
        Optional<Movie> movie = movieRepository.findById(1L);
        assertTrue(movie.isPresent());
        
        Movie inception = movie.get();
        assertEquals("Inception", inception.getTitle());
        assertEquals(2010, inception.getYear());
        assertEquals(8.8, inception.getImdbRating(), 0.1);
        assertEquals(148, inception.getDuration());
        
        System.out.println("✅ Film detayları doğru: " + inception.getTitle());
    }
} 