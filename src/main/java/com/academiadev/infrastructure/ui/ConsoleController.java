package com.academiadev.infrastructure.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.academiadev.application.repositories.UserRepository;
import com.academiadev.application.usecases.AbrirTicketUseCase;
import com.academiadev.application.usecases.AtenderTicketUseCase;
import com.academiadev.application.usecases.AtualizarProgressoUseCase;
import com.academiadev.application.usecases.GerarRelatorioUseCase;
import com.academiadev.application.usecases.MatricularAlunoUseCase;
import com.academiadev.domain.entities.Admin;
import com.academiadev.domain.entities.Student;
import com.academiadev.domain.entities.SupportTicket;
import com.academiadev.domain.entities.User;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.infrastructure.utils.GenericCsvExporter;

public class ConsoleController {
    private final MatricularAlunoUseCase matricularUseCase;
    private final GerarRelatorioUseCase relatorioUseCase;
    private final AbrirTicketUseCase abrirTicketUseCase;
    private final AtenderTicketUseCase atenderTicketUseCase;
    private final AtualizarProgressoUseCase atualizarProgressoUseCase;
    private final UserRepository userRepo; 

    private final Scanner scanner = new Scanner(System.in);
    private User usuarioLogado;

    public ConsoleController(MatricularAlunoUseCase matricular, 
                             GerarRelatorioUseCase relatorio,
                             AbrirTicketUseCase abrirTicket,
                             AtenderTicketUseCase atenderTicket,
                             AtualizarProgressoUseCase atualizarProgresso,
                             UserRepository userRepo) {
        this.matricularUseCase = matricular;
        this.relatorioUseCase = relatorio;
        this.abrirTicketUseCase = abrirTicket;
        this.atenderTicketUseCase = atenderTicket;
        this.atualizarProgressoUseCase = atualizarProgresso;
        this.userRepo = userRepo;
    }

    public void start() {
        System.out.println("=== AcademiaDev ===");
        while (true) {
            if (usuarioLogado == null) {
                fazerLogin();
            } else {
                if (usuarioLogado instanceof Admin) {
                    menuAdmin();
                } else {
                    menuAluno();
                }
            }
        }
    }

    private void fazerLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Digite seu email para entrar (ou 'sair'): ");
        String email = scanner.nextLine();
        
        if (email.equalsIgnoreCase("sair")) System.exit(0);

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            usuarioLogado = user.get();
            System.out.println("Bem-vindo, " + usuarioLogado.getName() + " (" + usuarioLogado.getClass().getSimpleName() + ")");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void menuAdmin() {
        System.out.println("\n[MENU ADMIN]");
        System.out.println("1. Atender Ticket (FIFO)");
        System.out.println("2. Relatório: Instrutores Ativos");
        System.out.println("3. Relatório: Média de Progresso Geral");
        System.out.println("4. Exportar CSV de Alunos");
        System.out.println("0. Logout");
        System.out.print("Opção: ");

        String op = scanner.nextLine();
        try {
            switch (op) {
                case "1":
                    SupportTicket ticket = atenderTicketUseCase.execute();
                    System.out.println("Atendendo ticket: " + ticket);
                    break;
                case "2":
                    System.out.println("Instrutores: " + relatorioUseCase.listarInstrutoresAtivos());
                    break;
                case "3":
                    System.out.printf("Média Geral: %.2f%%\n", relatorioUseCase.calcularMediaGeralProgresso());
                    break;
                case "4":
                    var alunosMap = relatorioUseCase.agruparAlunosPorPlano();
                    if (alunosMap.containsKey(SubscriptionPlan.PREMIUM)) {
                        List<Student> lista = alunosMap.get(SubscriptionPlan.PREMIUM);
                        System.out.println("Campos disponíveis: name, email");
                        System.out.println(GenericCsvExporter.exportToCsv(lista, Arrays.asList("name", "email")));
                    } else {
                        System.out.println("Nenhum aluno Premium para exportar.");
                    }
                    break;
                case "0": usuarioLogado = null; break;
                default: System.out.println("Inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void menuAluno() {
        System.out.println("\n[MENU ALUNO]");
        System.out.println("1. Matricular em Curso");
        System.out.println("2. Atualizar Progresso");
        System.out.println("3. Abrir Ticket de Suporte");
        System.out.println("0. Logout");
        System.out.print("Opção: ");

        String op = scanner.nextLine();
        try {
            switch (op) {
                case "1":
                    System.out.print("Nome do Curso: ");
                    String curso = scanner.nextLine();
                    matricularUseCase.execute(usuarioLogado.getEmail(), curso);
                    System.out.println("Matrícula realizada!");
                    break;
                case "2":
                    System.out.print("Nome do Curso: ");
                    String c = scanner.nextLine();
                    System.out.print("Novo Progresso (0-100): ");
                    int p = Integer.parseInt(scanner.nextLine());
                    atualizarProgressoUseCase.execute(usuarioLogado.getEmail(), c, p);
                    System.out.println("Atualizado!");
                    break;
                case "3":
                    System.out.print("Título: ");
                    String t = scanner.nextLine();
                    System.out.print("Mensagem: ");
                    String m = scanner.nextLine();
                    abrirTicketUseCase.execute(t, m);
                    System.out.println("Ticket criado!");
                    break;
                case "0": usuarioLogado = null; break;
                default: System.out.println("Inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}