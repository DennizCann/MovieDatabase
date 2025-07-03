package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.DirectorDTO;
import com.denizcan.moviedatabase.dto.AwardDTO;
import com.denizcan.moviedatabase.model.Director;

import java.util.stream.Collectors;

public class DirectorMapper {
    public static DirectorDTO toDTO(Director director) {
        DirectorDTO dto = new DirectorDTO();
        dto.setId(director.getId());
        dto.setName(director.getName());
        dto.setImageUrl(director.getImageUrl());
        
        // Ödül bilgilerini de dahil et
        if (director.getAwards() != null) {
            dto.setAwards(
                director.getAwards().stream()
                    .map(AwardMapper::toDTO)
                    .collect(Collectors.toSet())
            );
        }
        
        return dto;
    }
} 