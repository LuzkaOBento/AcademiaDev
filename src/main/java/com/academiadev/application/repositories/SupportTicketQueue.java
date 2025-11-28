package com.academiadev.application.repositories;

import java.util.Optional;

import com.academiadev.domain.entities.SupportTicket;


public interface SupportTicketQueue {
    void addTicket(SupportTicket ticket);
    Optional<SupportTicket> nextTicket(); 
    boolean isEmpty();
}