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

## 🧩 Microsserviços

### 🔵 Usuario Service (Porta 8080)

-   PostgreSQL
-   Spring Security + JWT
-   Gestão de usuários, autenticação e dados relacionais

### 🟠 Tarefas Core (Porta 8081)

-   MongoDB
-   CRUD de tarefas
-   Regras de datas e persistência schemaless

### 🟣 Notificação Service (Porta 8082)

-   Thymeleaf
-   JavaMail
-   Geração de e-mails HTML responsivos via SMTP

### 🟢 BFF Agendador (Porta 8083)

-   OpenFeign
-   Scheduler
-   API Gateway e motor de automação

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
