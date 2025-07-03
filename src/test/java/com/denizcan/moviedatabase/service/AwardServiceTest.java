package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.model.AwardCategory;
import com.denizcan.moviedatabase.model.AwardTitle;
import com.denizcan.moviedatabase.repository.AwardRepository;
import com.denizcan.moviedatabase.service.impl.AwardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {

    @Mock
    private AwardRepository awardRepository;

    @InjectMocks
    private AwardServiceImpl awardService;

    private Award testAward;

    @BeforeEach
    void setUp() {
        testAward = new Award();
        testAward.setId(1L);
        testAward.setName(AwardTitle.OSCAR);
        testAward.setYear(2020);
        testAward.setCategory(AwardCategory.BEST_PICTURE);
    }

    @Test
    void testSaveAward() {
        // Given
        when(awardRepository.save(any(Award.class))).thenReturn(testAward);

        // When
        Award savedAward = awardService.save(testAward);

        // Then
        assertNotNull(savedAward);
        assertEquals(AwardTitle.OSCAR, savedAward.getName());
        assertEquals(2020, savedAward.getYear());
        assertEquals(AwardCategory.BEST_PICTURE, savedAward.getCategory());
        verify(awardRepository, times(1)).save(testAward);
    }

    @Test
    void testFindById_WhenAwardExists() {
        // Given
        when(awardRepository.findById(1L)).thenReturn(Optional.of(testAward));

        // When
        Optional<Award> foundAward = awardService.findById(1L);

        // Then
        assertTrue(foundAward.isPresent());
        assertEquals(AwardTitle.OSCAR, foundAward.get().getName());
        assertEquals(2020, foundAward.get().getYear());
        verify(awardRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenAwardDoesNotExist() {
        // Given
        when(awardRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Award> foundAward = awardService.findById(999L);

        // Then
        assertFalse(foundAward.isPresent());
        verify(awardRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAll() {
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

        List<Award> awards = Arrays.asList(award1, award2);
        when(awardRepository.findAll()).thenReturn(awards);

        // When
        List<Award> foundAwards = awardService.findAll();

        // Then
        assertEquals(2, foundAwards.size());
        assertEquals(AwardTitle.OSCAR, foundAwards.get(0).getName());
        assertEquals(AwardTitle.GOLDEN_GLOBE, foundAwards.get(1).getName());
        assertEquals(2020, foundAwards.get(0).getYear());
        assertEquals(2021, foundAwards.get(1).getYear());
        verify(awardRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(awardRepository).deleteById(1L);

        // When
        awardService.deleteById(1L);

        // Then
        verify(awardRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSaveAwardWithDifferentCategories() {
        // Given
        Award bestActorAward = new Award();
        bestActorAward.setName(AwardTitle.OSCAR);
        bestActorAward.setYear(2019);
        bestActorAward.setCategory(AwardCategory.BEST_ACTOR);

        when(awardRepository.save(any(Award.class))).thenReturn(bestActorAward);

        // When
        Award savedAward = awardService.save(bestActorAward);

        // Then
        assertNotNull(savedAward);
        assertEquals(AwardCategory.BEST_ACTOR, savedAward.getCategory());
        assertEquals(2019, savedAward.getYear());
        verify(awardRepository, times(1)).save(bestActorAward);
    }

    @Test
    void testSaveAwardWithDifferentAwardTitles() {
        // Given
        Award baftaAward = new Award();
        baftaAward.setName(AwardTitle.BAFTA);
        baftaAward.setYear(2022);
        baftaAward.setCategory(AwardCategory.BEST_SCREENPLAY);

        when(awardRepository.save(any(Award.class))).thenReturn(baftaAward);

        // When
        Award savedAward = awardService.save(baftaAward);

        // Then
        assertNotNull(savedAward);
        assertEquals(AwardTitle.BAFTA, savedAward.getName());
        assertEquals(AwardCategory.BEST_SCREENPLAY, savedAward.getCategory());
        verify(awardRepository, times(1)).save(baftaAward);
    }

    @Test
    void testFindAll_WhenNoAwardsExist() {
        // Given
        when(awardRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Award> foundAwards = awardService.findAll();

        // Then
        assertNotNull(foundAwards);
        assertTrue(foundAwards.isEmpty());
        verify(awardRepository, times(1)).findAll();
    }

    @Test
    void testSaveMultipleAwards() {
        // Given
        Award award1 = new Award();
        award1.setName(AwardTitle.CANNES);
        award1.setYear(2023);
        award1.setCategory(AwardCategory.BEST_FOREIGN_LANGUAGE_FILM);

        Award award2 = new Award();
        award2.setName(AwardTitle.EMMY);
        award2.setYear(2023);
        award2.setCategory(AwardCategory.BEST_DOCUMENTARY);

        when(awardRepository.save(award1)).thenReturn(award1);
        when(awardRepository.save(award2)).thenReturn(award2);

        // When
        Award savedAward1 = awardService.save(award1);
        Award savedAward2 = awardService.save(award2);

        // Then
        assertNotNull(savedAward1);
        assertNotNull(savedAward2);
        assertEquals(AwardTitle.CANNES, savedAward1.getName());
        assertEquals(AwardTitle.EMMY, savedAward2.getName());
        verify(awardRepository, times(1)).save(award1);
        verify(awardRepository, times(1)).save(award2);
    }
} 