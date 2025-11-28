package com.academiadev.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.domain.exceptions.EnrollmentException;
import com.academiadev.infrastructure.persistence.CourseRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.UserRepositoryEmMemoria;

class AtualizarProgressoUseCaseTest {

    private AtualizarProgressoUseCase useCase;
    private UserRepository userRepo;
    private EnrollmentRepository enrollRepo;
    private CourseRepository courseRepo;

    @BeforeEach
    void setup() {
        userRepo = new UserRepositoryEmMemoria();
        enrollRepo = new EnrollmentRepositoryEmMemoria();
        courseRepo = new CourseRepositoryEmMemoria();
        useCase = new AtualizarProgressoUseCase(enrollRepo, userRepo);
    }

    @Test
    void deveAtualizarProgressoComSucesso() {
        Student s = new Student("João", "joao@email.com", SubscriptionPlan.BASIC);
        Course c = new Course("Java", "Prof", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE);
        Enrollment e = new Enrollment(s, c);
        
        userRepo.save(s);
        courseRepo.save(c);
        enrollRepo.save(e);

        useCase.execute("joao@email.com", "Java", 50);

        assertEquals(50, e.getProgress());
    }

    @Test
    void deveFalharSeAlunoNaoExiste() {
        assertThrows(EnrollmentException.class, () -> 
            useCase.execute("fantasma@email.com", "Java", 10)
        );
    }

    @Test
    void deveFalharSeMatriculaNaoExiste() {
        Student s = new Student("João", "joao@email.com", SubscriptionPlan.BASIC);
        userRepo.save(s); 

        assertThrows(EnrollmentException.class, () -> 
            useCase.execute("joao@email.com", "Java", 10)
        );
    }
}