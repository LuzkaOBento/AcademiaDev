package com.academiadev.infrastructure.persistence;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

import com.academiadev.application.repositories.SupportTicketQueue;
import com.academiadev.domain.entities.SupportTicket;

public class SupportTicketQueueEmMemoria implements SupportTicketQueue {
    private final Queue<SupportTicket> queue = new ArrayDeque<>();

    @Override
    public void addTicket(SupportTicket ticket) {
        queue.add(ticket);
    }

    @Override
    public Optional<SupportTicket> nextTicket() {
        return Optional.ofNullable(queue.poll()); 
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}