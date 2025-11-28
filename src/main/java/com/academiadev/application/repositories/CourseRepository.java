package com.academiadev.application.repositories;

import com.academiadev.domain.entities.Course;
import java.util.Optional;
import java.util.List;

public interface CourseRepository {
    void save(Course course);
    Optional<Course> findByTitle(String title);
    List<Course> findAll();
}