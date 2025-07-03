package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.MovieDTO;
import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;

import java.util.HashSet;
import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setYear(movie.getYear());
        dto.setImdbRating(movie.getImdbRating());
        dto.setImageUrl(movie.getImageUrl());
        dto.setDuration(movie.getDuration());
        dto.setPartOfSeries(movie.isPartOfSeries());
        dto.setSynopsis(movie.getSynopsis());
        if (movie.getGenre() != null) {
            dto.setGenres(
                movie.getGenre().stream()
                    .map(Enum::name)
                    .collect(Collectors.toSet())
            );
        } else {
            dto.setGenres(new java.util.HashSet<>());
        }
        return dto;
    }

    public static Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setYear(dto.getYear());
        movie.setImdbRating(dto.getImdbRating() > 0 ? dto.getImdbRating() : 0.0);
        movie.setImageUrl(dto.getImageUrl());
        movie.setDuration(dto.getDuration());
        movie.setPartOfSeries(dto.isPartOfSeries());
        movie.setSynopsis(dto.getSynopsis());
        if (dto.getGenres() != null && !dto.getGenres().isEmpty()) {
            movie.setGenre(
                dto.getGenres().stream()
                    .map(MovieGenre::valueOf)
                    .collect(Collectors.toSet())
            );
        }
        return movie;
    }
}
