package com.academiadev.application.usecases;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Admin;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.infrastructure.persistence.CourseRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.UserRepositoryEmMemoria;

class GerarRelatorioUseCaseTest {

    private GerarRelatorioUseCase useCase;
    private CourseRepository courseRepo;
    private EnrollmentRepository enrollRepo;
    private UserRepository userRepo;

    @BeforeEach
    void setup() {
        courseRepo = new CourseRepositoryEmMemoria();
        enrollRepo = new EnrollmentRepositoryEmMemoria();
        userRepo = new UserRepositoryEmMemoria();
        useCase = new GerarRelatorioUseCase(courseRepo, enrollRepo, userRepo);
    }

    @Test
    void deveListarCursosPorNivelOrdenados() {
        courseRepo.save(new Course("B-Curso", "Prof", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE));
        courseRepo.save(new Course("A-Curso", "Prof", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE));
        courseRepo.save(new Course("C-Outro", "Prof", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));

        List<Course> result = useCase.listarCursosPorNivel(DifficultyLevel.ADVANCED);

        assertEquals(2, result.size());
        assertEquals("A-Curso", result.get(0).getTitle()); 
        assertEquals("B-Curso", result.get(1).getTitle());
    }

    @Test
    void deveListarInstrutoresUnicos() {
        courseRepo.save(new Course("Java", "Yoda", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE));
        courseRepo.save(new Course("Spring", "Yoda", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE)); 
        courseRepo.save(new Course("HTML", "Obi-Wan", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));
        courseRepo.save(new Course("Cobol", "Vader", DifficultyLevel.BEGINNER, CourseStatus.INACTIVE)); 

        Set<String> instrutores = useCase.listarInstrutoresAtivos();

        assertEquals(2, instrutores.size());
        assertTrue(instrutores.contains("Yoda"));
        assertTrue(instrutores.contains("Obi-Wan"));
    }

    @Test
    void deveCalcularMediaGeral() {
        Student s1 = new Student("S1", "s1@a.com", SubscriptionPlan.BASIC);
        Enrollment e1 = new Enrollment(s1, null);
        e1.updateProgress(100);
        enrollRepo.save(e1);

        Student s2 = new Student("S2", "s2@a.com", SubscriptionPlan.BASIC);
        Enrollment e2 = new Enrollment(s2, null);
        e2.updateProgress(50);
        enrollRepo.save(e2);

        double media = useCase.calcularMediaGeralProgresso();
        assertEquals(75.0, media); 
    }
    
    @Test
    void deveAgruparAlunosPorPlano() {
        userRepo.save(new Student("S1", "s1@a.com", SubscriptionPlan.BASIC));
        userRepo.save(new Student("S2", "s2@a.com", SubscriptionPlan.BASIC));
        userRepo.save(new Student("S3", "s3@a.com", SubscriptionPlan.PREMIUM));
        userRepo.save(new Admin("Adm", "adm@a.com")); 

        Map<SubscriptionPlan, List<Student>> mapa = useCase.agruparAlunosPorPlano();

        assertEquals(2, mapa.get(SubscriptionPlan.BASIC).size());
        assertEquals(1, mapa.get(SubscriptionPlan.PREMIUM).size());
    }
}