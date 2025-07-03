package com.denizcan.moviedatabase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(description = "Film veri transfer objesi")
public class MovieDTO {
    @Schema(description = "Film ID'si", example = "1")
    private Long id;
    
    @Schema(description = "Film başlığı", example = "Inception", required = true)
    private String title;
    
    @Schema(description = "Yayın yılı", example = "2010", required = true)
    private Integer year;
    
    @Schema(description = "Film türleri", example = "[\"SCIENCE_FICTION\", \"THRILLER\"]")
    private Set<String> genres;
    
    @Schema(description = "IMDB puanı", example = "8.8", minimum = "0.0", maximum = "10.0")
    private double imdbRating;
    
    @Schema(description = "Film poster URL'si", example = "https://example.com/inception.jpg")
    private String imageUrl;
    
    @Schema(description = "Film süresi (dakika)", example = "148")
    private Integer duration;
    
    @Schema(description = "Seri film mi?", example = "false")
    private Boolean partOfSeries;
    
    @Schema(description = "Film özeti", example = "Rüya içinde rüya konseptini işleyen bilim kurgu filmi")
    private String synopsis;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Set<String> getGenres() { return genres; }
    public void setGenres(Set<String> genres) { this.genres = genres; }
    public double getImdbRating() { return imdbRating; }
    public void setImdbRating(double imdbRating) { this.imdbRating = imdbRating; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Boolean isPartOfSeries() { return partOfSeries; }
    public void setPartOfSeries(Boolean partOfSeries) { this.partOfSeries = partOfSeries; }
    
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
} 