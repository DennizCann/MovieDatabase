package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Actor;
import com.denizcan.moviedatabase.service.ActorService;
import com.denizcan.moviedatabase.dto.ActorDTO;
import com.denizcan.moviedatabase.mapper.ActorMapper;
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

@WebMvcTest(ActorController.class)
class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Actor testActor;
    private ActorDTO testActorDTO;

    @BeforeEach
    void setUp() {
        testActor = new Actor();
        testActor.setId(1L);
        testActor.setName("Leonardo DiCaprio");
        testActor.setImageUrl("https://example.com/leonardo.jpg");

        testActorDTO = ActorMapper.toDTO(testActor);
    }

    @Test
    void testGetAllActors() throws Exception {
        // Given
        Actor actor1 = new Actor();
        actor1.setId(1L);
        actor1.setName("Leonardo DiCaprio");

        Actor actor2 = new Actor();
        actor2.setId(2L);
        actor2.setName("Tom Hanks");

        when(actorService.findAll()).thenReturn(Arrays.asList(actor1, actor2));

        // When & Then
        mockMvc.perform(get("/actors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Leonardo DiCaprio"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Tom Hanks"));

        verify(actorService, times(1)).findAll();
    }

    @Test
    void testGetActorById_WhenActorExists() throws Exception {
        // Given
        when(actorService.findById(1L)).thenReturn(Optional.of(testActor));

        // When & Then
        mockMvc.perform(get("/actors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Leonardo DiCaprio"))
                .andExpect(jsonPath("$.imageUrl").value("https://example.com/leonardo.jpg"));

        verify(actorService, times(1)).findById(1L);
    }

    @Test
    void testGetActorById_WhenActorDoesNotExist() throws Exception {
        // Given
        when(actorService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/actors/999"))
                .andExpect(status().isNotFound());

        verify(actorService, times(1)).findById(999L);
    }

    @Test
    void testCreateActor() throws Exception {
        // Given
        when(actorService.save(any(Actor.class))).thenReturn(testActor);

        // When & Then
        mockMvc.perform(post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testActor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Leonardo DiCaprio"));

        verify(actorService, times(1)).save(any(Actor.class));
    }

    @Test
    void testUpdateActor_WhenActorExists() throws Exception {
        // Given
        when(actorService.findById(1L)).thenReturn(Optional.of(testActor));
        when(actorService.save(any(Actor.class))).thenReturn(testActor);

        // When & Then
        mockMvc.perform(put("/actors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testActor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Leonardo DiCaprio"));

        verify(actorService, times(1)).findById(1L);
        verify(actorService, times(1)).save(any(Actor.class));
    }

    @Test
    void testUpdateActor_WhenActorDoesNotExist() throws Exception {
        // Given
        when(actorService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/actors/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testActor)))
                .andExpect(status().isNotFound());

        verify(actorService, times(1)).findById(999L);
        verify(actorService, never()).save(any(Actor.class));
    }

    @Test
    void testDeleteActor_WhenActorExists() throws Exception {
        // Given
        when(actorService.findById(1L)).thenReturn(Optional.of(testActor));
        doNothing().when(actorService).deleteById(1L);

        // When & Then
        mockMvc.perform(delete("/actors/1"))
                .andExpect(status().isNoContent());

        verify(actorService, times(1)).findById(1L);
        verify(actorService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteActor_WhenActorDoesNotExist() throws Exception {
        // Given
        when(actorService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/actors/999"))
                .andExpect(status().isNotFound());

        verify(actorService, times(1)).findById(999L);
        verify(actorService, never()).deleteById(anyLong());
    }

    @Test
    void testCreateActorWithMinimalData() throws Exception {
        // Given
        Actor minimalActor = new Actor();
        minimalActor.setName("Brad Pitt");
        // imageUrl null bırakıldı

        when(actorService.save(any(Actor.class))).thenReturn(minimalActor);

        // When & Then
        mockMvc.perform(post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(minimalActor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Brad Pitt"));

        verify(actorService, times(1)).save(any(Actor.class));
    }

    @Test
    void testGetAllActors_WhenNoActorsExist() throws Exception {
        // Given
        when(actorService.findAll()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/actors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(actorService, times(1)).findAll();
    }

    @Test
    void testUpdateActorWithDifferentData() throws Exception {
        // Given
        Actor updatedActor = new Actor();
        updatedActor.setName("Updated Actor Name");
        updatedActor.setImageUrl("https://example.com/updated.jpg");

        when(actorService.findById(1L)).thenReturn(Optional.of(testActor));
        when(actorService.save(any(Actor.class))).thenReturn(updatedActor);

        // When & Then
        mockMvc.perform(put("/actors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedActor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Actor Name"))
                .andExpect(jsonPath("$.imageUrl").value("https://example.com/updated.jpg"));

        verify(actorService, times(1)).findById(1L);
        verify(actorService, times(1)).save(any(Actor.class));
    }
} 