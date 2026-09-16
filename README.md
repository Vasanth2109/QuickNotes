# QuickNotes

A full-stack notes/task management application built with a Spring Boot REST API backend and a React.js frontend, backed by PostgreSQL and fully containerized with Docker.

## Features

- Create, read, update, and delete notes
- Mark notes as done / pending
- Persistent storage in PostgreSQL
- Responsive React UI
- Fully tested service layer (100% unit test coverage)
- One-command startup via Docker Compose

## Tech Stack

**Backend:** Java, Spring Boot, Spring Data JPA, Hibernate, PostgreSQL
**Frontend:** React.js, Vite
**Testing:** JUnit 5, Mockito
**DevOps:** Docker, Docker Compose

## Project Structure

QuickNotes/
├── src/ # Spring Boot backend
├── quicknotes-frontend/ # React frontend
├── Dockerfile
├── docker-compose.yml
└── pom.xml


## Getting Started

### Prerequisites

- Docker Desktop
- Node.js and npm

### Run the backend (API + PostgreSQL)

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`

### Run the frontend

```bash
cd quicknotes-frontend
npm install
npm run dev
```

The app will be available at `http://localhost:5173`

## API Endpoints

| Method | Endpoint                | Description          |
|--------|--------------------------|-----------------------|
| POST   | `/api/notes`             | Create a note         |
| GET    | `/api/notes`             | Get all notes         |
| GET    | `/api/notes/{id}`        | Get a note by ID      |
| PUT    | `/api/notes/{id}`        | Update a note         |
| DELETE | `/api/notes/{id}`        | Delete a note         |
| PATCH  | `/api/notes/{id}/done`   | Mark a note as done   |

## Testing

Service layer tests are located in `src/test/java/com/quicknotes/service/NoteServiceTest.java`, covering all CRUD operations and error paths using JUnit 5 and Mockito.

```bash
mvn test
```