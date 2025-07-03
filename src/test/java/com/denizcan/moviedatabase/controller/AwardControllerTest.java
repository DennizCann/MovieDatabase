package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.model.AwardCategory;
import com.denizcan.moviedatabase.model.AwardTitle;
import com.denizcan.moviedatabase.service.AwardService;
import com.denizcan.moviedatabase.dto.AwardDTO;
import com.denizcan.moviedatabase.mapper.AwardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AwardController.class)
class AwardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AwardService awardService;

    @Autowired
    private ObjectMapper objectMapper;

    private Award testAward;
    private AwardDTO testAwardDTO;

    @BeforeEach
    void setUp() {
        testAward = new Award();
        testAward.setId(1L);
        testAward.setName(AwardTitle.OSCAR);
        testAward.setYear(2020);
        testAward.setCategory(AwardCategory.BEST_PICTURE);

        testAwardDTO = AwardMapper.toDTO(testAward);
    }

    @Test
    void testGetAllAwards() throws Exception {
        // Given
        Award award1 = new Award();
        award1.setId(1L);
        award1.setName(AwardTitle.OSCAR);
        award1.setYear(2020);
        award1.setCategory(AwardCategory.BEST_PICTURE);

        Award award2 = new Award();
        award2.setId(2L);
        award2.setName(AwardTitle.GOLDEN_GLOBE);
        award2.setYear(2021);
        award2.setCategory(AwardCategory.BEST_DIRECTOR);

        when(awardService.findAll()).thenReturn(Arrays.asList(award1, award2));

        // When & Then
        mockMvc.perform(get("/awards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("OSCAR"))
                .andExpect(jsonPath("$[0].year").value(2020))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("GOLDEN_GLOBE"))
                .andExpect(jsonPath("$[1].year").value(2021));

        verify(awardService, times(1)).findAll();
    }

    @Test
    void testGetAwardById_WhenAwardExists() throws Exception {
        // Given
        when(awardService.findById(1L)).thenReturn(Optional.of(testAward));

        // When & Then
        mockMvc.perform(get("/awards/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OSCAR"))
                .andExpect(jsonPath("$.year").value(2020))
                .andExpect(jsonPath("$.category").value("BEST_PICTURE"));

        verify(awardService, times(1)).findById(1L);
    }

    @Test
    void testGetAwardById_WhenAwardDoesNotExist() throws Exception {
        // Given
        when(awardService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/awards/999"))
                .andExpect(status().isNotFound());

        verify(awardService, times(1)).findById(999L);
    }

    @Test
    void testCreateAward() throws Exception {
        // Given
        when(awardService.save(any(Award.class))).thenReturn(testAward);

        // When & Then
        mockMvc.perform(post("/awards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAward)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OSCAR"))
                .andExpect(jsonPath("$.year").value(2020));

        verify(awardService, times(1)).save(any(Award.class));
    }

    @Test
    void testUpdateAward_WhenAwardExists() throws Exception {
        // Given
        when(awardService.findById(1L)).thenReturn(Optional.of(testAward));
        when(awardService.save(any(Award.class))).thenReturn(testAward);

        // When & Then
        mockMvc.perform(put("/awards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAward)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OSCAR"))
                .andExpect(jsonPath("$.year").value(2020));

        verify(awardService, times(1)).findById(1L);
        verify(awardService, times(1)).save(any(Award.class));
    }

    @Test
    void testUpdateAward_WhenAwardDoesNotExist() throws Exception {
        // Given
        when(awardService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/awards/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAward)))
                .andExpect(status().isNotFound());

        verify(awardService, times(1)).findById(999L);
        verify(awardService, never()).save(any(Award.class));
    }

    @Test
    void testDeleteAward_WhenAwardExists() throws Exception {
        // Given
        when(awardService.findById(1L)).thenReturn(Optional.of(testAward));
        doNothing().when(awardService).deleteById(1L);

        // When & Then
        mockMvc.perform(delete("/awards/1"))
                .andExpect(status().isNoContent());

        verify(awardService, times(1)).findById(1L);
        verify(awardService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAward_WhenAwardDoesNotExist() throws Exception {
        // Given
        when(awardService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/awards/999"))
                .andExpect(status().isNotFound());

        verify(awardService, times(1)).findById(999L);
        verify(awardService, never()).deleteById(anyLong());
    }

    @Test
    void testCreateAwardWithDifferentCategory() throws Exception {
        // Given
        Award bestActorAward = new Award();
        bestActorAward.setName(AwardTitle.OSCAR);
        bestActorAward.setYear(2019);
        bestActorAward.setCategory(AwardCategory.BEST_ACTOR);

        when(awardService.save(any(Award.class))).thenReturn(bestActorAward);

        // When & Then
        mockMvc.perform(post("/awards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bestActorAward)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("OSCAR"))
                .andExpect(jsonPath("$.category").value("BEST_ACTOR"))
                .andExpect(jsonPath("$.year").value(2019));

        verify(awardService, times(1)).save(any(Award.class));
    }

    @Test
    void testCreateAwardWithDifferentAwardTitle() throws Exception {
        // Given
        Award baftaAward = new Award();
        baftaAward.setName(AwardTitle.BAFTA);
        baftaAward.setYear(2022);
        baftaAward.setCategory(AwardCategory.BEST_SCREENPLAY);

        when(awardService.save(any(Award.class))).thenReturn(baftaAward);

        // When & Then
        mockMvc.perform(post("/awards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(baftaAward)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BAFTA"))
                .andExpect(jsonPath("$.category").value("BEST_SCREENPLAY"))
                .andExpect(jsonPath("$.year").value(2022));

        verify(awardService, times(1)).save(any(Award.class));
    }

    @Test
    void testGetAllAwards_WhenNoAwardsExist() throws Exception {
        // Given
        when(awardService.findAll()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/awards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(awardService, times(1)).findAll();
    }

    @Test
    void testUpdateAwardWithDifferentData() throws Exception {
        // Given
        Award updatedAward = new Award();
        updatedAward.setName(AwardTitle.CANNES);
        updatedAward.setYear(2023);
        updatedAward.setCategory(AwardCategory.BEST_FOREIGN_LANGUAGE_FILM);

        when(awardService.findById(1L)).thenReturn(Optional.of(testAward));
        when(awardService.save(any(Award.class))).thenReturn(updatedAward);

        // When & Then
        mockMvc.perform(put("/awards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAward)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CANNES"))
                .andExpect(jsonPath("$.category").value("BEST_FOREIGN_LANGUAGE_FILM"))
                .andExpect(jsonPath("$.year").value(2023));

        verify(awardService, times(1)).findById(1L);
        verify(awardService, times(1)).save(any(Award.class));
    }

    @Test
    void testCreateAwardWithEmmy() throws Exception {
        // Given
        Award emmyAward = new Award();
        emmyAward.setName(AwardTitle.EMMY);
        emmyAward.setYear(2023);
        emmyAward.setCategory(AwardCategory.BEST_DOCUMENTARY);

        when(awardService.save(any(Award.class))).thenReturn(emmyAward);

        // When & Then
        mockMvc.perform(post("/awards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emmyAward)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("EMMY"))
                .andExpect(jsonPath("$.category").value("BEST_DOCUMENTARY"))
                .andExpect(jsonPath("$.year").value(2023));

        verify(awardService, times(1)).save(any(Award.class));
    }
} 