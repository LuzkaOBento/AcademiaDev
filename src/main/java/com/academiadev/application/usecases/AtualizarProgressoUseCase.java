package com.academiadev.application.usecases;

import java.util.List;

import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.exceptions.EnrollmentException;

public class AtualizarProgressoUseCase {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public AtualizarProgressoUseCase(EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public void execute(String userEmail, String courseTitle, int newProgress) {
        Student student = (Student) userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EnrollmentException("Aluno não encontrado"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
        
        Enrollment enrollment = enrollments.stream()
            .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle))
            .findFirst()
            .orElseThrow(() -> new EnrollmentException("Matrícula não encontrada neste curso."));

        enrollment.updateProgress(newProgress);
    }
}