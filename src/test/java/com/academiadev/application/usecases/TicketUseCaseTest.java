package com.academiadev.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.academiadev.application.repositories.SupportTicketQueue;
import com.academiadev.domain.entities.SupportTicket;
import com.academiadev.domain.exceptions.EnrollmentException;
import com.academiadev.infrastructure.persistence.SupportTicketQueueEmMemoria;

class TicketUseCaseTest {

    private SupportTicketQueue queue;
    private AbrirTicketUseCase abrirUseCase;
    private AtenderTicketUseCase atenderUseCase;

    @BeforeEach
    void setup() {
        queue = new SupportTicketQueueEmMemoria();
        abrirUseCase = new AbrirTicketUseCase(queue);
        atenderUseCase = new AtenderTicketUseCase(queue);
    }

    @Test
    void deveAbrirEAtenderTicketNaOrdemCorreta() {
        // Arrange: Abre 2 tickets
        abrirUseCase.execute("Erro Login", "Não consigo logar"); 
        abrirUseCase.execute("Dúvida Java", "Como usar Streams?"); 

        SupportTicket t1 = atenderUseCase.execute();
        assertEquals("Erro Login", t1.getTitle());

        SupportTicket t2 = atenderUseCase.execute();
        assertEquals("Dúvida Java", t2.getTitle());
    }

    @Test
    void deveLancarErroSeFilaEstiverVazia() {
        assertThrows(EnrollmentException.class, () -> {
            atenderUseCase.execute();
        });
    }
}