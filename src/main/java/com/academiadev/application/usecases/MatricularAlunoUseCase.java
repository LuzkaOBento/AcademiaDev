package com.academiadev.application.usecases;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.exceptions.EnrollmentException;

public class MatricularAlunoUseCase {
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;

    public MatricularAlunoUseCase(UserRepository userRepo, CourseRepository courseRepo, EnrollmentRepository enrollmentRepo) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public void execute(String email, String courseTitle) {
        Student student = (Student) userRepo.findByEmail(email)
            .orElseThrow(() -> new EnrollmentException("Aluno não encontrado"));
        Course course = courseRepo.findByTitle(courseTitle)
            .orElseThrow(() -> new EnrollmentException("Curso não encontrado"));

    
        if (course.getStatus() != CourseStatus.ACTIVE) throw new EnrollmentException("Curso inativo");
        if (!student.canEnroll()) throw new EnrollmentException("Limite de plano atingido");
        if (enrollmentRepo.existsByStudentAndCourse(student, courseTitle)) throw new EnrollmentException("Já matriculado");

    
        enrollmentRepo.save(new Enrollment(student, course));
        student.incrementEnrollments(); 
    }
}