package com.academiadev.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.academiadev.domain.entities.Admin;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.entities.SupportTicket;
import com.academiadev.domain.enums.SubscriptionPlan;

class EntitiesCoverageTest {

    @Test
    void deveCobrirMetodosDeAdminEUser() {
        Admin admin = new Admin("Chefe", "admin@email.com");
        assertEquals("Chefe", admin.getName());
        assertEquals("admin@email.com", admin.getEmail());
    }

    @Test
    void deveCobrirMetodosDeStudent() {
        Student student = new Student("Aluno", "aluno@email.com", SubscriptionPlan.BASIC);
        assertEquals(SubscriptionPlan.BASIC, student.getSubscriptionPlan());
        
        student.incrementEnrollments();
        student.decrementEnrollments();
        student.decrementEnrollments(); 
        assertTrue(student.canEnroll());
    }

    @Test
    void deveCobrirMetodosDeSupportTicket() {
        SupportTicket t = new SupportTicket("Erro", "Ajuda");
        assertNotNull(t.getId());
        assertEquals("Erro", t.getTitle());
        assertEquals("Ajuda", t.getMessage());
        
        assertTrue(t.toString().contains("Ticket #")); 
    }
}