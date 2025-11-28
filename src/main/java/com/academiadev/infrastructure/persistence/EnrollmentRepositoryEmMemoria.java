package com.academiadev.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.domain.entities.Enrollment;
import com.academiadev.domain.entities.Student;

public class EnrollmentRepositoryEmMemoria implements EnrollmentRepository {
    private List<Enrollment> db = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        db.add(enrollment);
    }

    @Override
    public List<Enrollment> findByStudent(Student student) {
        return db.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByStudentAndCourse(Student student, String courseTitle) {
        return db.stream()
                .anyMatch(e -> e.getStudent().getEmail().equals(student.getEmail()) 
                            && e.getCourse().getTitle().equals(courseTitle));
    }
    
    @Override
    public List<Enrollment> findAll() { return new ArrayList<>(db); }
}