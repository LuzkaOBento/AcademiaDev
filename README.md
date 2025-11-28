# AcademiaDev - Clean Architecture Prototype

Este projeto é um protótipo de plataforma de cursos online desenvolvido em Java, seguindo estritamente os princípios da **Clean Architecture** e **TDD** (Test Driven Development).

## 🏗 Estrutura do Projeto e Regra da Dependência

O projeto respeita a regra onde as camadas internas não conhecem as externas:

### 1. Domain (Núcleo)
Contém as regras de negócio puras e agnósticas a frameworks.
- **Entidades**: `Student`, `Admin`, `Course`, `Enrollment`, `SupportTicket`.
- **Regras**: Ex: `Student.canEnroll()` verifica o limite de cursos baseado no plano (Basic/Premium) sem acessar banco de dados.

### 2. Application (Casos de Uso)
Orquestra o fluxo de dados para cumprir os requisitos funcionais.
- **UseCases**: 
  - `MatricularAlunoUseCase`: Valida regras complexas e persiste a matrícula.
  - `AtenderTicketUseCase`: Garante a ordem FIFO ao processar tickets.
  - `GerarRelatorioUseCase`: Usa Java Streams para processar dados analíticos.
- **Interfaces**: Define contratos (`UserRepository`, `SupportTicketQueue`) que a infraestrutura deve implementar.

### 3. Infrastructure (Detalhes)
Implementações concretas que podem ser substituídas sem afetar o núcleo.
- **Persistência**: Repositórios em memória (`HashMap`) simulam um banco de dados.
- **Estruturas de Dados**: `SupportTicketQueueEmMemoria` utiliza `ArrayDeque` para garantir comportamento de fila (FIFO) eficiente.
- **Utils**: `GenericCsvExporter` usa **Reflection** para gerar CSVs de qualquer objeto dinamicamente, mantendo essa complexidade técnica longe do domínio.

## 💉 Injeção de Dependência
A classe `Main.java` atua como a **Raiz de Composição**. Ela instancia as implementações concretas (Infrastructure) e as injeta nos construtores dos UseCases (Application).
- Isso permite que o sistema seja testável e desacoplado.
- Exemplo: `AtenderTicketUseCase` recebe uma interface `SupportTicketQueue`, sem saber que por trás existe um `ArrayDeque` em memória.

## 🧪 TDD e Testes
O projeto inclui testes unitários (JUnit 5) focados no Domínio e na Aplicação, garantindo que as regras de negócio (como limite de matrículas) funcionem independentemente da interface gráfica ou banco de dados.

## 🚀 Como Rodar
1. Certifique-se de ter o Maven instalado (ou use o wrapper/IDE).
2. Execute a classe `Main.java` para iniciar a CLI.
3. **Logins de Teste**:
   - Admin: `admin@academiadev.com`
   - Aluno Básico: `joao@email.com`
   - Aluno Premium: `maria@email.com`