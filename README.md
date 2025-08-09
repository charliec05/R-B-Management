# R-B-Management
# Bearing Factory Inventory Management

A full-stack application for managing bearings, raw materials, warehouses, costs, and employees with role-based access control (RBAC).

## Features
- JWT-based authentication (login with email/password)
- Role-based permissions (ADMIN, WAREHOUSE_OPS, READONLY)
- Manage items (bearings, raw materials)
- Manage warehouses
- Receive stock into inventory lots
- View current stock levels

---

## Tech Stack
**Backend**
- Java 17 + Spring Boot 3
- Spring Web, Spring Data JPA, Spring Security, Validation
- PostgreSQL + Flyway migrations
- MapStruct, Lombok
- JUnit 5 + Testcontainers

**Frontend**
- React 18 + TypeScript + Vite
- React Router, TanStack Query, React Hook Form
- Axios for API calls

**DevOps**
- Docker Compose for local development
- Multi-stage Dockerfiles for backend & frontend
- Makefile commands for quick setup

---

## Prerequisites
- [Docker Desktop](https://docs.docker.com/desktop/)
- Make (comes pre-installed on macOS/Linux, install separately on Windows via WSL or Git Bash)

---

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/your-username/factory-inventory.git
cd factory-inventory
