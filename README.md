# 📌 Concurso Task API

API REST desenvolvida em **Java + Spring Boot** para gerenciamento de tarefas, usuários e categorias.  
O projeto foi criado como parte dos estudos para o concurso de **Técnico Judiciário - Apoio Especializado / Programador de Computador (TJPE)**.

---

## ✨ Funcionalidades

- CRUD completo para **tarefas**, **categorias** e **usuários**
- Autenticação com **JWT**
- Documentação interativa com **Swagger**
- Banco de dados via **Docker**
- Testes automatizados
- Migrations para versionamento do banco

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (para testes)
- **PostgreSQL** (produção/desenvolvimento)
- **Flyway** (migrations)
- **Swagger/OpenAPI**
- **Docker**

---

## 📂 Estrutura do Projeto

concurso-task/
├── src/
│ ├── main/
│ │ ├── java/com/... → Código fonte
│ │ ├── resources/ → application.properties, migrations, templates
│ └── test/ → Testes automatizados
├── pom.xml → Dependências Maven
├── Dockerfile → Configuração Docker
└── README.md → Documentação

yaml
Copiar
Editar

---

## ⚙️ Como Rodar Localmente

### 1️⃣ Clonar o repositório
``bash
git clone https://github.com/seu-usuario/concurso-task.git
cd concurso-task

## 2️⃣ Rodar com Docker
Certifique-se de ter Docker e Docker Compose instalados.
docker build -t concurso-task .
docker run -p 8080:8080 concurso-task


O projeto estará disponível em:
http://localhost:8080

## 📖 Documentação da API
Após iniciar a aplicação, acesse o Swagger:
http://localhost:8080/swagger-ui/index.html

## 🔑 Autenticação
A API utiliza JWT para autenticação.

Fluxo básico:
Registrar usuário → POST /auth/register

Fazer login → POST /auth/login → receber token JWT

Enviar o token no Authorization Header:
Authorization: Bearer seu_token_aqui

## 🧪 Rodando os Testes
bash
./mvnw test

🛠 Endpoints Principais
Método	Endpoint	Descrição
POST	/auth/register	Registro de novo usuário
POST	/auth/login	Login e geração de JWT
GET	/tasks	Lista todas as tarefas
POST	/tasks	Cria nova tarefa
PUT	/tasks/{id}	Atualiza tarefa existente
DELETE	/tasks/{id}	Remove tarefa

## 📌 Observações
Por padrão, o projeto sobe com banco H2 em memória para testes.

Para uso com PostgreSQL, configure as variáveis no application.properties ou use application-prod.properties.

As migrations estão em:
src/main/resources/db_migrations


👤 Autor
João Guilherme
📧 Email: contato.joaojgn@gmail.com
