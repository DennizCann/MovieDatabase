package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.ActorDTO;
import com.denizcan.moviedatabase.dto.AwardDTO;
import com.denizcan.moviedatabase.model.Actor;

import java.util.stream.Collectors;

public class ActorMapper {
    public static ActorDTO toDTO(Actor actor) {
        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setImageUrl(actor.getImageUrl());
        
        // Ödül bilgilerini de dahil et
        if (actor.getAwards() != null) {
            dto.setAwards(
                actor.getAwards().stream()
                    .map(AwardMapper::toDTO)
                    .collect(Collectors.toSet())
            );
        }
        
        return dto;
    }
} 