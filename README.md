# 📅 Agendador de Tarefas - Arquitetura de Microsserviços

Um sistema distribuído para gestão e notificação automática de tarefas,
construído com **Java 17**, **Spring Boot 3** e **Arquitetura de
Microsserviços**.

------------------------------------------------------------------------

## 🚀 Visão Geral

Este projeto é um ecossistema completo composto por **4 microsserviços**
que se comunicam via **OpenFeign** para garantir o agendamento,
segurança e notificação de eventos por e-mail.

A arquitetura foi desenhada para ser **escalável**, separando
responsabilidades de autenticação, regra de negócio (Core), notificações
e orquestração.

------------------------------------------------------------------------

## 🏗️ Arquitetura do Sistema

O sistema opera com um **BFF (Backend for Frontend)** atuando como
orquestrador central e Scheduler.

### 📌 Fluxo Geral

Cliente → BFF → Microsserviços (Auth, Core, Notificação)

### 🔄 Automação (Cron Job)

-   O BFF executa um **cron job a cada 5 minutos**
-   Realiza auto-login com conta de serviço
-   Busca tarefas próximas do horário de execução
-   Envia e-mail via microsserviço de Notificação
-   Atualiza status da tarefa para `NOTIFICADO`

------------------------------------------------------------------------

---

## 🧩 Arquitetura de Microsserviços

O ecossistema é composto por quatro serviços independentes, cada um com responsabilidade bem definida, seguindo princípios de **Single Responsibility** e **Arquitetura Distribuída**.

````mermaid
flowchart TB
    %% =========================
    %% 🎨 Definição de Estilos
    %% =========================
    classDef client fill:#fafafa,stroke:#1a1a1a,stroke-width:2px,color:#1a1a1a
    classDef gateway fill:#e3f2fd,stroke:#0d47a1,stroke-width:2px,color:#0d47a1,font-weight:bold
    classDef service fill:#fff8e1,stroke:#f57f17,stroke-width:2px,color:#4e342e
    classDef infra fill:#ede7f6,stroke:#5e35b1,stroke-width:2px,color:#311b92
    classDef database fill:#e8f5e9,stroke:#1b5e20,stroke-width:2px,color:#1b5e20
    classDef scheduler fill:#fce4ec,stroke:#ad1457,stroke-width:2px,color:#880e4f

    %% =========================
    %% 👤 Camada Cliente
    %% =========================
    Cliente["📱 Aplicação Cliente<br/>(Web / Mobile)"]:::client

    %% =========================
    %% 🚪 Camada de Orquestração
    %% =========================
    subgraph Camada_BFF ["Camada de Orquestração (Backend for Frontend)"]
        BFF["🚀 BFF Agendador<br/>(Spring Boot - :8083)"]:::gateway
        Agendador[["⏰ Agendador de Tarefas<br/>(Quartz / @Scheduled)"]]:::scheduler
    end

    %% =========================
    %% 🧠 Camada de Microsserviços
    %% =========================
    subgraph Microsservicos ["Camada de Domínio (Microsserviços)"]
        direction LR
        AuthService["🔐 Serviço de Identidade<br/>(User Service - :8080)<br/>JWT / Autenticação"]:::service
        TaskService["⚙️ Serviço de Tarefas<br/>(Core Service - :8081)<br/>Regras de Negócio"]:::service
        MailService["📧 Serviço de Notificação<br/>(Mail Service - :8082)<br/>Envio de Emails"]:::infra
    end

    %% =========================
    %% 🗄️ Camada de Persistência
    %% =========================
    subgraph Persistencia ["Camada de Dados"]
        Postgres[("🐘 PostgreSQL<br/>Dados de Usuário")]:::database
        Mongo[("🍃 MongoDB<br/>Tarefas e Eventos")]:::database
    end

    %% =========================
    %% 🔄 Fluxo Externo
    %% =========================
    Cliente -->|"HTTP REST<br/>JSON + JWT"| BFF

    %% =========================
    %% 🔗 Comunicação Interna
    %% =========================
    BFF <-->|"OpenFeign<br/>Validação de Token"| AuthService
    BFF <-->|"OpenFeign<br/>CRUD de Tarefas"| TaskService
    BFF -->|"OpenFeign<br/>Disparo de Email"| MailService

    %% =========================
    %% 💾 Acesso a Banco
    %% =========================
    AuthService --- Postgres
    TaskService --- Mongo

    %% =========================
    %% ⏳ Automação (Fluxo Assíncrono)
    %% =========================
    Agendador -.->|"1️⃣ Execução via Cron"| BFF
    BFF -.->|"2️⃣ Buscar Tarefas Pendentes"| TaskService
    BFF -.->|"3️⃣ Enviar Notificações"| MailService
    BFF -.->|"4️⃣ Atualizar Status"| TaskService

    %% =========================
    %% 🎯 Estilização de Links
    %% =========================
    linkStyle 0 stroke:#0d47a1,stroke-width:2px
    linkStyle 1,2,3 stroke:#424242,stroke-width:1.5px
    linkStyle 6,7,8,9 stroke:#c62828,stroke-width:2px,stroke-dasharray: 5 5

````
---

### 🔵 Usuario Service  
📍 **Porta:** `8080`  
🔗 **Repositório:** https://github.com/vsalescode/usuario  

**Responsabilidade:** Identity Provider e gestão de usuários.

**Stack Principal:**
- PostgreSQL  
- Spring Security  
- JWT (Autenticação Stateless)  
- JPA / Hibernate  

**Funções:**
- Cadastro e autenticação de usuários  
- Emissão de Token JWT  
- Gestão de endereços e telefones  
- Proteção de rotas sensíveis  

---

### 🟠 Tarefas Core  
📍 **Porta:** `8081`  
🔗 **Repositório:** https://github.com/vsalescode/agendador-tarefas  

**Responsabilidade:** Núcleo de regras de negócio e persistência de tarefas.

**Stack Principal:**
- MongoDB  
- Spring Data MongoDB  
- OpenFeign (integração com User Service)  

**Funções:**
- CRUD de tarefas  
- Consultas por intervalo de tempo  
- Gerenciamento de status (`PENDENTE → NOTIFICADO → CANCELADO`)  
- Base de dados otimizada para o Scheduler  

---

### 🟣 Notification Service  
📍 **Porta:** `8082`  
🔗 **Repositório:** https://github.com/vsalescode/notificacao  

**Responsabilidade:** Renderização e envio de e-mails.

**Stack Principal:**
- Thymeleaf  
- Spring Mail (JavaMailSender)  
- SMTP  

**Funções:**
- Processamento de templates HTML  
- Envio de e-mails responsivos  
- Tratamento de falhas SMTP  
- Serviço stateless (sem banco de dados)  

---

### 🟢 BFF Agendador  
📍 **Porta:** `8083`  
🔗 **Repositório:** Este serviço  

**Responsabilidade:** Orquestração e automação do sistema.

**Stack Principal:**
- Spring Cloud OpenFeign  
- Scheduler (Cron Job)  
- API Gateway  

**Funções:**
- Gateway central para o front-end  
- Auto-login para geração de JWT  
- Execução periódica do cron  
- Coordenação entre Core e Notification  
- Atualização automática de status  

---

------------------------------------------------------------------------

## 🛠️ Tecnologias Utilizadas

-   Java 17
-   Spring Boot 3
-   Spring Security & JWT
-   Spring Cloud OpenFeign
-   MongoDB
-   PostgreSQL
-   Thymeleaf
-   Swagger / OpenAPI
-   Docker (Opcional)

------------------------------------------------------------------------

## ⚙️ Como Executar

### ✅ Pré-requisitos

-   Java 17+
-   Maven
-   MongoDB
-   PostgreSQL

### 🗄️ Banco de Dados

Crie:

-   PostgreSQL: `db_usuarios`
-   MongoDB: `db_agendador`

### ▶️ Ordem de Inicialização

1.  Usuario Service (8080)
2.  Tarefas Core (8081)
3.  Notificação Service (8082)
4.  BFF Agendador (8083)

### 🧪 Testes

Acesse:

    http://localhost:8083/swagger-ui.html

1.  Crie um usuário
2.  Faça login
3.  Cadastre uma tarefa
4.  Aguarde o cron executar

------------------------------------------------------------------------

## 👨‍💻 Autor

Desenvolvido por **João Victor**

🔗 [LinkedIn](https://www.linkedin.com/in/vsalescode/)
🌐 [Portfólio](https://portfolio-vsalescode.vercel.app/)



    
