package com.academiadev.application.usecases;

import com.academiadev.application.repositories.SupportTicketQueue;
import com.academiadev.domain.entities.SupportTicket;
import com.academiadev.domain.exceptions.EnrollmentException; 

public class AtenderTicketUseCase {
    private final SupportTicketQueue queue;

    public AtenderTicketUseCase(SupportTicketQueue queue) {
        this.queue = queue;
    }

    public SupportTicket execute() {
        if (queue.isEmpty()) {
            throw new EnrollmentException("A fila de suporte está vazia.");
        }

        return queue.nextTicket().orElseThrow(() -> new EnrollmentException("Erro ao recuperar ticket."));
    }
}