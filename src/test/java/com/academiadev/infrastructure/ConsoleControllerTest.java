package com.academiadev.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.academiadev.application.repositories.CourseRepository;
import com.academiadev.application.repositories.EnrollmentRepository;
import com.academiadev.application.repositories.SupportTicketQueue;
import com.academiadev.application.repositories.UserRepository;
import com.academiadev.application.usecases.AbrirTicketUseCase;
import com.academiadev.application.usecases.AtenderTicketUseCase;
import com.academiadev.application.usecases.AtualizarProgressoUseCase;
import com.academiadev.application.usecases.GerarRelatorioUseCase;
import com.academiadev.application.usecases.MatricularAlunoUseCase;
import com.academiadev.domain.entities.Admin;
import com.academiadev.domain.entities.Course;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.CourseStatus;
import com.academiadev.domain.enums.DifficultyLevel;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.infrastructure.persistence.CourseRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;
import com.academiadev.infrastructure.persistence.SupportTicketQueueEmMemoria;
import com.academiadev.infrastructure.persistence.UserRepositoryEmMemoria;
import com.academiadev.infrastructure.ui.ConsoleController;

class ConsoleControllerTest {

    @Test
    void testeDeIntegracaoInterfaceAdmin() {
        UserRepository userRepo = new UserRepositoryEmMemoria();
        CourseRepository courseRepo = new CourseRepositoryEmMemoria();
        EnrollmentRepository enrollRepo = new EnrollmentRepositoryEmMemoria();
        SupportTicketQueue queue = new SupportTicketQueueEmMemoria();

        userRepo.save(new Admin("Admin", "admin@test.com"));
        
        MatricularAlunoUseCase matricular = new MatricularAlunoUseCase(userRepo, courseRepo, enrollRepo);
        GerarRelatorioUseCase relatorio = new GerarRelatorioUseCase(courseRepo, enrollRepo, userRepo);
        AbrirTicketUseCase abrir = new AbrirTicketUseCase(queue);
        AtenderTicketUseCase atender = new AtenderTicketUseCase(queue);
        AtualizarProgressoUseCase att = new AtualizarProgressoUseCase(enrollRepo, userRepo);

        String inputs = "admin@test.com\n" + 
                        "0\n" +              
                        "sair\n";          

        System.setIn(new ByteArrayInputStream(inputs.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConsoleController controller = new ConsoleController(matricular, relatorio, abrir, atender, att, userRepo);
        controller.start();

        String saida = outContent.toString();
        assertTrue(saida.contains("[MENU ADMIN]"));
    }

    @Test
    void testeDeIntegracaoInterfaceAluno() {

        UserRepositoryEmMemoria userRepo = new UserRepositoryEmMemoria();
        CourseRepositoryEmMemoria courseRepo = new CourseRepositoryEmMemoria();
        EnrollmentRepositoryEmMemoria enrollRepo = new EnrollmentRepositoryEmMemoria();
        SupportTicketQueueEmMemoria queue = new SupportTicketQueueEmMemoria();
        
 
        userRepo.save(new Student("Aluno", "aluno@test.com", SubscriptionPlan.PREMIUM));
        courseRepo.save(new Course("Java", "Prof", DifficultyLevel.BEGINNER, CourseStatus.ACTIVE));

        MatricularAlunoUseCase matricular = new MatricularAlunoUseCase(userRepo, courseRepo, enrollRepo);
        GerarRelatorioUseCase relatorio = new GerarRelatorioUseCase(courseRepo, enrollRepo, userRepo);
        AbrirTicketUseCase abrir = new AbrirTicketUseCase(queue);
        AtenderTicketUseCase atender = new AtenderTicketUseCase(queue);
        AtualizarProgressoUseCase att = new AtualizarProgressoUseCase(enrollRepo, userRepo);

    
        String inputs = "aluno@test.com\n" +  
                        "1\n" +              
                        "Java\n" +            
                        "0\n" +            
                        "sair\n";            

        System.setIn(new java.io.ByteArrayInputStream(inputs.getBytes()));
        
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()));

        ConsoleController controller = new ConsoleController(matricular, relatorio, abrir, atender, att, userRepo);
        controller.start();

        assertTrue(enrollRepo.existsByStudentAndCourse(
            (Student)userRepo.findByEmail("aluno@test.com").get(), "Java"
        ));
    }
}