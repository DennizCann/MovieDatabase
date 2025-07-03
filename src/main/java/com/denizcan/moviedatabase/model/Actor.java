package com.denizcan.moviedatabase.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "actor_award",
        joinColumns = @JoinColumn(name = "actor_id"),
        inverseJoinColumns = @JoinColumn(name = "award_id")
    )
    private Set<Award> awards;

    @ManyToMany(mappedBy = "cast")
    private Set<Movie> movies;

    private String imageUrl; // Oyuncunun fotoğrafı veya görselinin yolu/URL'si

    // Default constructor
    public Actor() {}

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Award> getAwards() { return awards; }
    public void setAwards(Set<Award> awards) { this.awards = awards; }

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
} 