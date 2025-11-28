package com.academiadev.main;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.Admin;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;

public class InitialData {
    public static void populate(UserRepository userRepo, CourseRepository courseRepo) {
        userRepo.save(new Admin("Admin Boss", "admin@academiadev.com"));

        userRepo.save(new Student("João Silva", "joao@email.com", SubscriptionPlan.BASIC));
        userRepo.save(new Student("Maria Dev", "maria@email.com", SubscriptionPlan.PREMIUM));

        courseRepo.save(new Course("Java Clean Architecture", "Otávio Lemos", DifficultyLevel.ADVANCED, CourseStatus.ACTIVE));
        courseRepo.save(new Course("Spring Boot Essencial", "Michelli Brito", DifficultyLevel.INTERMEDIATE, CourseStatus.ACTIVE));
        courseRepo.save(new Course("Lógica de Programação", "Gustavo Guanabara", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));
        courseRepo.save(new Course("Cobol Legacy", "Dinossauro Jr", DifficultyLevel.ADVANCED, CourseStatus.INACTIVE));
    }
}