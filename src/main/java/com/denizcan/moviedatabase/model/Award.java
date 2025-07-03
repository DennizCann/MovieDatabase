package com.denizcan.moviedatabase.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AwardTitle name;
    @Column(name = "award_year")
    private int year;
    @Enumerated(EnumType.STRING)
    private AwardCategory category;

    @ManyToMany(mappedBy = "awards")
    private Set<Movie> movies;

    @ManyToMany(mappedBy = "awards")
    private Set<Actor> actors;

    @ManyToMany(mappedBy = "awards")
    private Set<Director> directors;

    // Default constructor
    public Award() {}

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AwardTitle getName() { return name; }
    public void setName(AwardTitle name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public AwardCategory getCategory() { return category; }
    public void setCategory(AwardCategory category) { this.category = category; }

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }

    public Set<Actor> getActors() { return actors; }
    public void setActors(Set<Actor> actors) { this.actors = actors; }

    public Set<Director> getDirectors() { return directors; }
    public void setDirectors(Set<Director> directors) { this.directors = directors; }
} 