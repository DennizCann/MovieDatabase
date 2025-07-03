package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.AwardDTO;
import com.denizcan.moviedatabase.model.Award;

public class AwardMapper {
    public static AwardDTO toDTO(Award award) {
        AwardDTO dto = new AwardDTO();
        dto.setId(award.getId());
        dto.setName(award.getName().name());
        dto.setYear(award.getYear());
        dto.setCategory(award.getCategory().name());
        return dto;
    }
} 