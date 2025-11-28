package com.academiadev.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.domain.entities.Course;

public class CourseRepositoryEmMemoria implements CourseRepository {
    
    private final Map<String, Course> db = new HashMap<>();

    @Override
    public void save(Course course) {
        db.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(db.get(title));
    }
    
    @Override
    public List<Course> findAll() {
        return db.values().stream().collect(Collectors.toList());
    }
}