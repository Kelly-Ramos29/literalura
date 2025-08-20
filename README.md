# 📚 Desafio Literalura

Este projeto é um desafio de programação proposto pela **Alura**, focado na criação de uma aplicação **Java com Spring Boot** para interação com a API **Gutendex**.  
A aplicação permite buscar livros por título, persistir os dados em um banco de dados **PostgreSQL**, e realizar diversas consultas sobre livros e autores.

---

## 🚀 Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot** – Criação da aplicação
- **Maven** – Gerenciamento de dependências
- **PostgreSQL** – Banco de dados relacional
- **Spring Data JPA** – Acesso e manipulação de dados
- **Jackson (ObjectMapper)** – Mapeamento JSON para objetos Java

---

## ✨ Funcionalidades
✅ Buscar livros por título na API Gutendex  
✅ Persistir dados de livros e autores no banco de dados  
✅ Listar todos os autores registrados  
✅ Listar todos os livros registrados  
✅ Consultar autores vivos em um determinado ano  
✅ Listar livros por idioma específico  

---

## ⚙️ Como Rodar o Projeto

### 🔧 Pré-requisitos
- Java 17 ou superior instalado  
- Maven instalado  
- PostgreSQL configurado  

### 🗄️ Configuração do Banco de Dados
1. Crie um banco de dados no PostgreSQL (exemplo: `literalura_db`).  
2. Atualize o arquivo `src/main/resources/application.properties` com as credenciais do seu banco de dados:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
