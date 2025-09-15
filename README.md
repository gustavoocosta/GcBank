# 🏦 GcBank

![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-Build-green?logo=gradle&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)

## 📌 Visão Geral
O **GcBank** é um sistema bancário desenvolvido em **Java**, com o objetivo de simular as principais operações de um banco de forma simples e organizada.  

O projeto tem como foco o **aprendizado de conceitos de programação orientada a objetos**, organização de código e boas práticas no desenvolvimento back-end.  

---

## ⚙️ Tecnologias Utilizadas
- **Java** (versão 17 ou superior recomendada)  
- **Gradle** (gerenciador de build e dependências)  
- **Banco de dados em memória** (pode ser adaptado para persistência real)  

---

## 📂 Estrutura do Projeto
```bash
GcBank/
├── src/                       # Código-fonte do projeto
│   ├── model/                 # Classes de domínio (Cliente, Conta, etc.)
│   ├── service/               # Regras de negócio (Operações bancárias)
│   ├── repository/            # Persistência em memória
│   └── Main.java              # Ponto de entrada da aplicação
├── build.gradle               # Configurações do Gradle
├── settings.gradle            # Configuração do projeto
├── gradlew / gradlew.bat      # Scripts de execução do Gradle
├── README.md                  # Descrição do projeto
└── HELP.md                    # Instruções adicionais
```

---

## 💳 Funcionalidades
O sistema permite as seguintes operações:

- ✅ Cadastro de clientes  
- ✅ Criação de contas bancárias  
- ✅ Depósitos  
- ✅ Saques  
- ✅ Transferências entre contas  
- ✅ Consulta de saldo  

---

## ▶️ Como Executar
1. **Clonar o repositório**
   ```bash
   git clone https://github.com/gustavoocosta/GcBank.git
   cd GcBank
   ```

2. **Compilar o projeto**
   ```bash
   ./gradlew build
   ```

3. **Executar**
   ```bash
   ./gradlew run
   ```

---

## 🗄️ Modelo de Dados
O sistema é composto pelas seguintes entidades principais:

**Cliente**
```text
id
nome
cpf
```

**Conta**
```text
id
numeroConta
saldo
cliente (associação)
```

**Transações**
```text
Registro de depósitos, saques e transferências
```

---

## 📌 Exemplos de Uso
- Criar cliente → retorna ID e associa conta  
- Depositar → aumenta saldo da conta  
- Sacar → diminui saldo (se houver fundos suficientes)  
- Transferir → debita da conta origem e credita na conta destino  
- Consultar saldo → retorna valor atual  

---

## 🚀 Melhorias Futuras
- Persistência em banco de dados real (PostgreSQL/MySQL)  
- API REST (com Spring Boot ou Quarkus)  
- Autenticação de usuários (login/senha)  
- Testes unitários e de integração  
- Interface gráfica (Web ou Desktop)  

---

👨‍💻 **Autor:** Gustavo Costa
