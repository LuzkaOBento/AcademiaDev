package com.academiadev.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.SubscriptionPlan;

class StudentTest {

    @Test
    @DisplayName("Deve permitir matrícula se o plano Básico não atingiu o limite (menos de 3)")
    void devePermitirMatriculaPlanoBasico() {
        Student student = new Student("João", "joao@email.com", SubscriptionPlan.BASIC);
        
        student.incrementEnrollments();
        student.incrementEnrollments();

        assertTrue(student.canEnroll(), "Deveria permitir pois tem apenas 2 matrículas");
    }

    @Test
    @DisplayName("Não deve permitir matrícula se o plano Básico atingiu o limite (3)")
    void naoDevePermitirMatriculaPlanoBasicoCheio() {
        Student student = new Student("João", "joao@email.com", SubscriptionPlan.BASIC);
        
        student.incrementEnrollments();
        student.incrementEnrollments();
        student.incrementEnrollments();

        assertFalse(student.canEnroll(), "Não deveria permitir pois já tem 3 matrículas");
    }

    @Test
    @DisplayName("Deve permitir matrícula ilimitada para plano Premium")
    void devePermitirMatriculaPlanoPremium() {
        Student student = new Student("Maria", "maria@email.com", SubscriptionPlan.PREMIUM);
        
        for (int i = 0; i < 10; i++) {
            student.incrementEnrollments();
        }

        assertTrue(student.canEnroll(), "Premium deve sempre permitir matrículas");
    }
}