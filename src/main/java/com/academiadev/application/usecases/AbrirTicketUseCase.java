package com.academiadev.application.usecases;

import com.academiadev.application.repositories.SupportTicketQueue;
import com.academiadev.domain.entities.SupportTicket;

public class AbrirTicketUseCase {
    private final SupportTicketQueue queue;

    public AbrirTicketUseCase(SupportTicketQueue queue) {
        this.queue = queue;
    }

    public void execute(String title, String message) {
        queue.addTicket(new SupportTicket(title, message));
    }
}