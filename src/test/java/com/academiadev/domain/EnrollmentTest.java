package com.academiadev.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;

class EnrollmentTest {

    @Test
    void deveIniciarComProgressoZero() {
        Student s = new Student("A", "a@a.com", SubscriptionPlan.BASIC);
        Course c = new Course("Java", "Instrutor", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Enrollment e = new Enrollment(s, c);

        assertEquals(0, e.getProgress());
    }

    @Test
    void deveAtualizarProgressoValido() {
        Student s = new Student("A", "a@a.com", SubscriptionPlan.BASIC);
        Course c = new Course("Java", "Instrutor", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Enrollment e = new Enrollment(s, c);

        e.updateProgress(50);
        assertEquals(50, e.getProgress());
    }

    @Test
    void deveLancarErroSeProgressoInvalido() {
        Student s = new Student("A", "a@a.com", SubscriptionPlan.BASIC);
        Course c = new Course("Java", "Instrutor", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Enrollment e = new Enrollment(s, c);

        assertThrows(IllegalArgumentException.class, () -> e.updateProgress(-1));
        assertThrows(IllegalArgumentException.class, () -> e.updateProgress(101));
    }
}