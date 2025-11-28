package com.academiadev.application.repositories;

import java.util.List;

import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findByStudent(Student student);
    boolean existsByStudentAndCourse(Student student, String courseTitle);
    
    List<Enrollment> findAll();
}