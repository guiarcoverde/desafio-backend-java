# Desafio - Backend

Este projeto foi desenvolvido para praticar habilidades em desenvolvimento backend utilizando **Java** e **Spring Boot**. Ele simula um sistema de transações financeiras inspirado no desafio de backend do PicPay.

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Spring Boot DevTools**
- **H2 Database** (banco de dados em memória para testes)
- **Lombok**

## 🚀 Como Executar o Projeto

### 🔧 Pré-requisitos
Antes de começar, certifique-se de ter instalado em sua máquina:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

### 📥 Clonar o repositório
```sh
  git clone https://github.com/seu-usuario/picpaydesafio.git
  cd picpaydesafio
```

### ▶️ Rodando o projeto
1. Compile o projeto:
```sh
mvn clean install
```

2. Execute a aplicação:
```sh
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📌 Endpoints Principais

### Criar uma transação
**POST** `/transactions`
```json
{
  "senderId": 1,
  "receiverId": 2,
  "amount": 100.00
}
```
**Resposta:**
```json
{
  "senderEmail": "user1@email.com",
  "receiverEmail": "user2@email.com",
  "amount": 100.00,
  "message": "Transaction successfully made. Notification sent.",
  "timestamp": "2025-03-24T12:00:00"
}
```

## 📝 Estrutura do Projeto
```
├── src/main/java/com/picpaydesafio/
│   ├── controllers/       # Controllers da API
│   ├── models/            # Modelos de dados (Entidades)
│   ├── repositories/      # Interfaces de acesso ao banco de dados
│   ├── services/          # Lógica de negócios
│   ├── exceptions/        # Exceções personalizadas
│   ├── PicpayDesafioApplication.java  # Classe principal do Spring Boot
```
