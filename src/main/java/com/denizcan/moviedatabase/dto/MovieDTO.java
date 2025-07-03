package com.denizcan.moviedatabase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.Set;

@Schema(description = "Film veri transfer objesi")
public class MovieDTO {
    @Schema(description = "Film ID'si", example = "1")
    private Long id;
    
    @NotBlank(message = "Film başlığı boş olamaz")
    @Size(min = 1, max = 255, message = "Film başlığı 1-255 karakter arasında olmalıdır")
    @Schema(description = "Film başlığı", example = "Inception", required = true)
    private String title;
    
    @NotNull(message = "Yayın yılı boş olamaz")
    @Min(value = 1888, message = "Yayın yılı 1888'den küçük olamaz")
    @Max(value = 2030, message = "Yayın yılı 2030'dan büyük olamaz")
    @Schema(description = "Yayın yılı", example = "2010", required = true)
    private Integer year;
    
    @Schema(description = "Film türleri", example = "[\"SCIENCE_FICTION\", \"THRILLER\"]")
    private Set<String> genres;
    
    @NotNull(message = "IMDB puanı boş olamaz")
    @DecimalMin(value = "0.0", message = "IMDB puanı 0'dan küçük olamaz")
    @DecimalMax(value = "10.0", message = "IMDB puanı 10'dan büyük olamaz")
    @Schema(description = "IMDB puanı", example = "8.8", minimum = "0.0", maximum = "10.0")
    private Double imdbRating;
    
    @Pattern(regexp = "^(https?://.*|)$", message = "Geçerli bir URL giriniz")
    @Schema(description = "Film poster URL'si", example = "https://example.com/inception.jpg")
    private String imageUrl;
    
    @Min(value = 1, message = "Film süresi en az 1 dakika olmalıdır")
    @Max(value = 600, message = "Film süresi en fazla 600 dakika olabilir")
    @Schema(description = "Film süresi (dakika)", example = "148")
    private Integer duration;
    
    @Schema(description = "Seri film mi?", example = "false")
    private Boolean partOfSeries;
    
    @Size(max = 1000, message = "Film özeti en fazla 1000 karakter olabilir")
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
    public Double getImdbRating() { return imdbRating; }
    public void setImdbRating(Double imdbRating) { this.imdbRating = imdbRating; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Boolean isPartOfSeries() { return partOfSeries; }
    public void setPartOfSeries(Boolean partOfSeries) { this.partOfSeries = partOfSeries; }
    
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
} 