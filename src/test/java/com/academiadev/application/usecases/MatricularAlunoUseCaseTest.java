package com.academiadev.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Admin;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.domain.exceptions.EnrollmentException;
import com.academiadev.infrastructure.persistence.CourseRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.UserRepositoryEmMemoria;

class MatricularAlunoUseCaseTest {

    private MatricularAlunoUseCase useCase;
    private UserRepository userRepo;
    private CourseRepository courseRepo;
    private EnrollmentRepository enrollRepo;

    @BeforeEach
    void setup() {
        userRepo = new UserRepositoryEmMemoria();
        courseRepo = new CourseRepositoryEmMemoria();
        enrollRepo = new EnrollmentRepositoryEmMemoria();
        useCase = new MatricularAlunoUseCase(userRepo, courseRepo, enrollRepo);
    }

    @Test
    void deveMatricularAlunoComSucesso() {
        userRepo.save(new Student("João", "joao@email.com", SubscriptionPlan.PREMIUM));
        courseRepo.save(new Course("Java", "Prof", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));

        useCase.execute("joao@email.com", "Java");

        assertTrue(enrollRepo.existsByStudentAndCourse(
            (Student) userRepo.findByEmail("joao@email.com").get(), "Java"
        ));
    }

    @Test
    void naoDeveMatricularEmCursoInativo() {
        userRepo.save(new Student("João", "joao@email.com", SubscriptionPlan.PREMIUM));
        courseRepo.save(new Course("Cobol", "Prof", DifficultyLevel.BEGINNER, CourseStatus.INACTIVE));

        Exception e = assertThrows(EnrollmentException.class, () -> {
            useCase.execute("joao@email.com", "Cobol");
        });
        assertEquals("Curso inativo", e.getMessage());
    }

    @Test
    void naoDeveMatricularDuasVezesNoMesmoCurso() {
        userRepo.save(new Student("João", "joao@email.com", SubscriptionPlan.PREMIUM));
        courseRepo.save(new Course("Java", "Prof", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));
        
        useCase.execute("joao@email.com", "Java");

        assertThrows(EnrollmentException.class, () -> {
            useCase.execute("joao@email.com", "Java");
        });
    }

    @Test
    void deveFalharSeAlunoNaoEncontradoNoBanco() {
        courseRepo.save(new Course("Java", "P", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));
        
        Exception e = assertThrows(EnrollmentException.class, () -> {
            useCase.execute("email_inexistente@a.com", "Java");
        });
        assertEquals("Aluno não encontrado", e.getMessage());
    }

    @Test
    void deveFalharSeCursoNaoEncontradoNoBanco() {
        userRepo.save(new Student("João", "joao@email.com", SubscriptionPlan.BASIC));
        
        Exception e = assertThrows(EnrollmentException.class, () -> {
            useCase.execute("joao@email.com", "Curso Fantasma");
        });
        assertEquals("Curso não encontrado", e.getMessage());
    }
    @Test
    void naoDeveMatricularAdminPoisNaoTemPlano() {
        userRepo.save(new Admin("Chefe", "admin@boss.com"));
        courseRepo.save(new Course("Java", "P", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));

        assertThrows(ClassCastException.class, () -> {
            useCase.execute("admin@boss.com", "Java");
        });
    }

    @Test
    void deveImpedirMatriculaSePlanoEstiverCheio() {
        Student student = new Student("João", "joao@email.com", SubscriptionPlan.BASIC);
        student.incrementEnrollments();
        student.incrementEnrollments();
        student.incrementEnrollments(); 
        
        userRepo.save(student);
        courseRepo.save(new Course("Java", "P", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));

        Exception e = assertThrows(EnrollmentException.class, () -> {
            useCase.execute("joao@email.com", "Java");
        });
        assertEquals("Limite de plano atingido", e.getMessage()); 
    }

    
}