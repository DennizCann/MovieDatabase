package com.denizcan.moviedatabase.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AwardTest {

    private Award award;

    @BeforeEach
    void setUp() {
        award = new Award();
    }

    @Test
    void testAwardCreation() {
        // Given
        award.setId(1L);
        award.setName(AwardTitle.OSCAR);
        award.setYear(2020);
        award.setCategory(AwardCategory.BEST_PICTURE);

        // When & Then
        assertEquals(1L, award.getId());
        assertEquals(AwardTitle.OSCAR, award.getName());
        assertEquals(2020, award.getYear());
        assertEquals(AwardCategory.BEST_PICTURE, award.getCategory());
    }

    @Test
    void testDifferentAwardTypes() {
        // Given
        Award oscar = new Award();
        oscar.setName(AwardTitle.OSCAR);
        oscar.setCategory(AwardCategory.BEST_ACTOR);

        Award goldenGlobe = new Award();
        goldenGlobe.setName(AwardTitle.GOLDEN_GLOBE);
        goldenGlobe.setCategory(AwardCategory.BEST_ACTRESS);

        Award bafta = new Award();
        bafta.setName(AwardTitle.BAFTA);
        bafta.setCategory(AwardCategory.BEST_DIRECTOR);

        // When & Then
        assertEquals(AwardTitle.OSCAR, oscar.getName());
        assertEquals(AwardCategory.BEST_ACTOR, oscar.getCategory());
        
        assertEquals(AwardTitle.GOLDEN_GLOBE, goldenGlobe.getName());
        assertEquals(AwardCategory.BEST_ACTRESS, goldenGlobe.getCategory());
        
        assertEquals(AwardTitle.BAFTA, bafta.getName());
        assertEquals(AwardCategory.BEST_DIRECTOR, bafta.getCategory());
    }

    @Test
    void testAwardEquality() {
        // Given
        Award award1 = new Award();
        award1.setId(1L);
        award1.setName(AwardTitle.OSCAR);
        award1.setYear(2020);

        Award award2 = new Award();
        award2.setId(1L);
        award2.setName(AwardTitle.OSCAR);
        award2.setYear(2020);

        // When & Then
        assertEquals(award1.getId(), award2.getId());
        assertEquals(award1.getName(), award2.getName());
        assertEquals(award1.getYear(), award2.getYear());
    }

    @Test
    void testAwardTitleEnum() {
        // Given & When & Then
        assertEquals("OSCAR", AwardTitle.OSCAR.name());
        assertEquals("GOLDEN_GLOBE", AwardTitle.GOLDEN_GLOBE.name());
        assertEquals("BAFTA", AwardTitle.BAFTA.name());
        assertEquals("CANNES", AwardTitle.CANNES.name());
        assertEquals("SAG", AwardTitle.SAG.name());
    }

    @Test
    void testAwardCategoryEnum() {
        // Given & When & Then
        assertEquals("BEST_PICTURE", AwardCategory.BEST_PICTURE.name());
        assertEquals("BEST_DIRECTOR", AwardCategory.BEST_DIRECTOR.name());
        assertEquals("BEST_ACTOR", AwardCategory.BEST_ACTOR.name());
        assertEquals("BEST_ACTRESS", AwardCategory.BEST_ACTRESS.name());
        assertEquals("BEST_SCREENPLAY", AwardCategory.BEST_SCREENPLAY.name());
    }
} 