package com.academiadev.domain.entities;

import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel; 

public class Course {
    private String title;
    private String instructorName;
    private DifficultyLevel difficultyLevel;
    private CourseStatus status;

    public Course(String title, String instructorName, DifficultyLevel difficultyLevel, CourseStatus status) {
        this.title = title;
        this.instructorName = instructorName;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
    }

    
    public String getTitle() { return title; }
    public CourseStatus getStatus() { return status; }
    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public String getInstructorName() { return instructorName; }
    public void setStatus(CourseStatus status) { this.status = status; }
}