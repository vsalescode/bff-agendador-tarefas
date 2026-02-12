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
flowchart TD
    User["📱 Cliente / Front-end"] -->|"HTTP/JSON"| BFF("🟢 BFF Agendador :8083")
    
    subgraph "Camada de Orquestração"
        BFF -->|"Validação & Login"| Auth("🔵 Usuário Service :8080")
        BFF -->|"Gestão de Tarefas"| Core("🟠 Tarefas Core :8081")
        BFF -->|"Disparo de Email"| Notif("🟣 Notificação Service :8082")
    end
    
    subgraph "Automação (Cron Job)"
        BFF -- "A cada 5 min" --> BFF
        BFF -- "Busca Tarefas Próximas" --> Core
        Core -- "Lista de Tarefas" --> BFF
        BFF -- "Envia Email HTML" --> Notif
        BFF -- "Atualiza Status" --> Core
    end

    Auth --> Postgres[("🐘 PostgreSQL")]
    Core --> Mongo[("🍃 MongoDB")]
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



    
