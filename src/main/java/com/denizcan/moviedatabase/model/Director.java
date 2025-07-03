package com.denizcan.moviedatabase.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl; // Yönetmenin fotoğrafı veya görselinin yolu/URL'si

    @ManyToMany
    @JoinTable(
        name = "director_award",
        joinColumns = @JoinColumn(name = "director_id"),
        inverseJoinColumns = @JoinColumn(name = "award_id")
    )
    private Set<Award> awards;

    @ManyToMany(mappedBy = "director")
    private Set<Movie> movies;

    // Default constructor
    public Director() {}

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Set<Award> getAwards() { return awards; }
    public void setAwards(Set<Award> awards) { this.awards = awards; }

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }
} 