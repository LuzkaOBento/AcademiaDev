package com.academiadev.application.usecases;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;

public class GerarRelatorioUseCase {
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final UserRepository userRepo;

    public GerarRelatorioUseCase(CourseRepository c, EnrollmentRepository e, UserRepository u) {
        this.courseRepo = c;
        this.enrollmentRepo = e;
        this.userRepo = u;
    }

    // 1. Cursos por Nível
    public List<Course> listarCursosPorNivel(DifficultyLevel level) {
        return courseRepo.findAll().stream()
                .filter(c -> c.getDifficultyLevel() == level)
                .sorted(Comparator.comparing(Course::getTitle)) // [cite: 80] Ordem alfabética
                .collect(Collectors.toList());
    }

    // 2. Instrutores Únicos
    public Set<String> listarInstrutoresAtivos() {
        return courseRepo.findAll().stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet()); // [cite: 81] Set garante unicidade
    }

    // 3. Agrupar Alunos por Plano
    public Map<SubscriptionPlan, List<Student>> agruparAlunosPorPlano() {
        return userRepo.findAll().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.groupingBy(Student::getSubscriptionPlan)); // [cite: 82]
    }

    // 4. Média de Progresso
    public double calcularMediaGeralProgresso() {
        return enrollmentRepo.findAll().stream()
                .mapToInt(Enrollment::getProgress)
                .average()
                .orElse(0.0); // [cite: 83]
    }
}