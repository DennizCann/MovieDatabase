package com.denizcan.moviedatabase.dto;

public class AwardDTO {
    private Long id;
    private String name;
    private int year;
    private String category;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
} 