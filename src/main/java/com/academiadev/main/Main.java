package com.academiadev.main;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.SupportTicketQueue;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.application.usecases.AbrirTicketUseCase;
import com.academiadev.application.usecases.AtenderTicketUseCase;
import com.academiadev.application.usecases.AtualizarProgressoUseCase;
import com.academiadev.application.usecases.GerarRelatorioUseCase;
import com.academiadev.application.usecases.MatricularAlunoUseCase;
import com.academiadev.infrastructure.persistence.CourseRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.SupportTicketQueueEmMemoria;
import com.academiadev.infrastructure.persistence.UserRepositoryEmMemoria;
import com.academiadev.infrastructure.ui.ConsoleController;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepositoryEmMemoria();
        CourseRepository courseRepo = new CourseRepositoryEmMemoria();
        EnrollmentRepository enrollRepo = new EnrollmentRepositoryEmMemoria();
        SupportTicketQueue ticketQueue = new SupportTicketQueueEmMemoria();

        InitialData.populate(userRepo, courseRepo);

        MatricularAlunoUseCase matricularUC = new MatricularAlunoUseCase(userRepo, courseRepo, enrollRepo);
        GerarRelatorioUseCase relatorioUC = new GerarRelatorioUseCase(courseRepo, enrollRepo, userRepo);
        AbrirTicketUseCase abrirTicketUC = new AbrirTicketUseCase(ticketQueue);
        AtenderTicketUseCase atenderTicketUC = new AtenderTicketUseCase(ticketQueue);
        AtualizarProgressoUseCase attProgressoUC = new AtualizarProgressoUseCase(enrollRepo, userRepo);

        ConsoleController controller = new ConsoleController(
            matricularUC, 
            relatorioUC, 
            abrirTicketUC, 
            atenderTicketUC, 
            attProgressoUC,
            userRepo 
        );

        controller.start();
    }
}