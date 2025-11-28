package com.academiadev.infrastructure;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;

class EnrollmentRepositoryTest {

    @Test
    void devePersistirEBuscarMatriculas() {
        EnrollmentRepositoryEmMemoria repo = new EnrollmentRepositoryEmMemoria();
        Student s = new Student("A", "a@a.com", SubscriptionPlan.BASIC);
        Course c = new Course("Java", "P", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Enrollment e = new Enrollment(s, c);

        repo.save(e);

        assertEquals(1, repo.findAll().size());

        List<Enrollment> lista = repo.findByStudent(s);
        assertEquals(1, lista.size());
        assertEquals("Java", lista.get(0).getCourse().getTitle());
    }

    @Test
    void deveRetornarVazioSeNaoEncontrar() {
        EnrollmentRepositoryEmMemoria repo = new EnrollmentRepositoryEmMemoria();
        Student s = new Student("B", "b@b.com", SubscriptionPlan.BASIC);
        
        assertTrue(repo.findByStudent(s).isEmpty());
        assertTrue(repo.findAll().isEmpty());
        assertFalse(repo.existsByStudentAndCourse(s, "Java"));
    }
}