package com.denizcan.moviedatabase.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name = "release_year")
    private Integer year;
    @ElementCollection(targetClass = MovieGenre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private Set<MovieGenre> genre;
    @ManyToMany
    @JoinTable(
        name = "movie_director",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> director;
    @ManyToMany
    @JoinTable(
        name = "movie_actor",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> cast;
    @ManyToMany
    @JoinTable(
        name = "movie_award",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "award_id")
    )
    private Set<Award> awards;
    @Column(length = 1000)
    private String synopsis;
    private BigDecimal budget;
    private BigDecimal boxOffice;
    private double imdbRating;
    private Boolean partOfSeries;
    private String seriesName;
    private Integer duration; // Film süresi, dakika cinsinden
    private String imageUrl; // Filmin görselinin yolu veya URL'si

    // Default constructor
    public Movie() {}

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Set<MovieGenre> getGenre() { return genre; }
    public void setGenre(Set<MovieGenre> genre) { this.genre = genre; }

    public Set<Director> getDirector() { return director; }
    public void setDirector(Set<Director> director) { this.director = director; }

    public Set<Actor> getCast() { return cast; }
    public void setCast(Set<Actor> cast) { this.cast = cast; }

    public Set<Award> getAwards() { return awards; }
    public void setAwards(Set<Award> awards) { this.awards = awards; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }

    public BigDecimal getBoxOffice() { return boxOffice; }
    public void setBoxOffice(BigDecimal boxOffice) { this.boxOffice = boxOffice; }

    public double getImdbRating() { return imdbRating; }
    public void setImdbRating(double imdbRating) { this.imdbRating = imdbRating; }

    public Boolean isPartOfSeries() { return partOfSeries; }
    public void setPartOfSeries(Boolean partOfSeries) { this.partOfSeries = partOfSeries; }

    public String getSeriesName() { return seriesName; }
    public void setSeriesName(String seriesName) { this.seriesName = seriesName; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}