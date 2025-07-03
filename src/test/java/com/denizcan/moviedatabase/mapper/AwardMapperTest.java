package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.AwardDTO;
import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.model.AwardCategory;
import com.denizcan.moviedatabase.model.AwardTitle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AwardMapperTest {

    private Award award;

    @BeforeEach
    void setUp() {
        award = new Award();
        award.setId(1L);
        award.setName(AwardTitle.OSCAR);
        award.setYear(2020);
        award.setCategory(AwardCategory.BEST_PICTURE);
    }

    @Test
    void testToDTO_WithBasicFields() {
        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithDifferentAwardTitle() {
        // Given
        award.setName(AwardTitle.GOLDEN_GLOBE);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("GOLDEN_GLOBE", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithDifferentCategory() {
        // Given
        award.setCategory(AwardCategory.BEST_ACTOR);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_ACTOR", dto.getCategory());
    }

    @Test
    void testToDTO_WithDifferentYear() {
        // Given
        award.setYear(2019);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2019, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithDifferentId() {
        // Given
        award.setId(999L);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(999L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithZeroId() {
        // Given
        award.setId(0L);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(0L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithNegativeId() {
        // Given
        award.setId(-1L);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(-1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithZeroYear() {
        // Given
        award.setYear(0);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(0, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithNegativeYear() {
        // Given
        award.setYear(-2020);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(-2020, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithAllAwardTitles() {
        // Test all award titles
        for (AwardTitle title : AwardTitle.values()) {
            // Given
            award.setName(title);

            // When
            AwardDTO dto = AwardMapper.toDTO(award);

            // Then
            assertEquals(title.name(), dto.getName());
        }
    }

    @Test
    void testToDTO_WithAllCategories() {
        // Test all award categories
        for (AwardCategory category : AwardCategory.values()) {
            // Given
            award.setCategory(category);

            // When
            AwardDTO dto = AwardMapper.toDTO(award);

            // Then
            assertEquals(category.name(), dto.getCategory());
        }
    }

    @Test
    void testToDTO_WithDifferentAwards() {
        // Given
        Award award1 = new Award();
        award1.setId(1L);
        award1.setName(AwardTitle.BAFTA);
        award1.setYear(2021);
        award1.setCategory(AwardCategory.BEST_DIRECTOR);

        Award award2 = new Award();
        award2.setId(2L);
        award2.setName(AwardTitle.CANNES);
        award2.setYear(2022);
        award2.setCategory(AwardCategory.BEST_SCREENPLAY);

        // When
        AwardDTO dto1 = AwardMapper.toDTO(award1);
        AwardDTO dto2 = AwardMapper.toDTO(award2);

        // Then
        assertEquals(1L, dto1.getId());
        assertEquals("BAFTA", dto1.getName());
        assertEquals(2021, dto1.getYear());
        assertEquals("BEST_DIRECTOR", dto1.getCategory());

        assertEquals(2L, dto2.getId());
        assertEquals("CANNES", dto2.getName());
        assertEquals(2022, dto2.getYear());
        assertEquals("BEST_SCREENPLAY", dto2.getCategory());
    }

    @Test
    void testToDTO_WithEmmyAward() {
        // Given
        award.setName(AwardTitle.EMMY);
        award.setCategory(AwardCategory.BEST_DOCUMENTARY);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("EMMY", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_DOCUMENTARY", dto.getCategory());
    }

    @Test
    void testToDTO_WithGrammyAward() {
        // Given
        award.setName(AwardTitle.GRAMMY);
        award.setCategory(AwardCategory.BEST_MUSIC);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("GRAMMY", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_MUSIC", dto.getCategory());
    }

    @Test
    void testToDTO_WithPalmeDorAward() {
        // Given
        award.setName(AwardTitle.PALME_DOR);
        award.setCategory(AwardCategory.BEST_FOREIGN_LANGUAGE_FILM);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("PALME_DOR", dto.getName());
        assertEquals(2020, dto.getYear());
        assertEquals("BEST_FOREIGN_LANGUAGE_FILM", dto.getCategory());
    }

    @Test
    void testToDTO_WithVeryOldYear() {
        // Given
        award.setYear(1929); // First Academy Awards

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(1929, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }

    @Test
    void testToDTO_WithFutureYear() {
        // Given
        award.setYear(2030);

        // When
        AwardDTO dto = AwardMapper.toDTO(award);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("OSCAR", dto.getName());
        assertEquals(2030, dto.getYear());
        assertEquals("BEST_PICTURE", dto.getCategory());
    }
} 