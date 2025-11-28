package com.academiadev.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.academiadev.domain.entities.Course;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;

class CourseTest {
    @Test
    void deveCriarCursoCorretamente() {
        Course c = new Course("Java", "Otavio", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);
        
        assertEquals("Java", c.getTitle());
        assertEquals("Otavio", c.getInstructorName());
        assertEquals(DifficultyLevel.ADVANCED, c.getDifficultyLevel());
        assertEquals(CourseStatus.ACTIVE, c.getStatus());
    }

    @Test
    void deveAlterarStatus() {
        Course c = new Course("Java", "Otavio", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE);
        c.setStatus(CourseStatus.INACTIVE);
        assertEquals(CourseStatus.INACTIVE, c.getStatus());
    }
}