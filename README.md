# Minicommerce Microservices

A containerized microservices-based e-commerce backend system built with Spring Boot.
This project demonstrates service separation, API Gateway routing, and independent database management using Docker Compose.

---

## Architecture

The system consists of three main services:

- **API Gateway** (Port 8080)
  Entry point for all client requests and routes traffic to internal services.

- **Catalog Service** (Port 8081)
  Manages product data including stock, price, and status.

- **Order Service** (Port 8082)
  Handles order creation, payment, and status lifecycle.

Each service has its own database to ensure loose coupling and scalability.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Docker & Docker Compose
- Maven

---

## Features

- Product management (CRUD, stock update, status control)
- Order management (create, pay, cancel)
- Pagination, filtering, and sorting
- API Gateway routing
- Database per service (Catalog & Order)
- Containerized deployment

---

## How to Run

### 1. Clone repository

```bash
git clone https://github.com/Firlyansyah/minicommerce-microservices.git
cd minicommerce-microservice
```

---

### 2. Run with Docker Compose

```bash
docker compose up --build
```

---

## Service Endpoints

| Service         | URL                   |
| --------------- | --------------------- |
| API Gateway     | http://localhost:8080 |
| Catalog Service | http://localhost:8081 |
| Order Service   | http://localhost:8082 |

---

## API Documentation

Swagger UI:

- Catalog Service:
  http://localhost:8081/swagger-ui.html

- Order Service:
  http://localhost:8082/swagger-ui.html

---

## Database

- MySQL (Dockerized)
- Databases:
  - `minicommerce_catalog`
  - `minicommerce_order`

Database initialization is handled automatically via Docker.

---

## Project Structure

```
minicommerce/
│
├── minicommerce-api-gateway/
├── minicommerce-catalog/
├── minicommerce-order/
├── mysql-init/
│   └── init.sql
├── docker-compose.yml
└── README.md
```

---

## Key Design Decisions

- **Database per service**
  Ensures isolation and independent scalability.

- **API Gateway pattern**
  Centralizes request routing and simplifies client interaction.

- **Docker Compose orchestration**
  Enables one-command deployment for all services.

---

## Example Request

```bash
curl -X GET "http://localhost:8080/api/products?page=0&size=10"
```

---

## Purpose

This project demonstrates practical understanding of:

- Microservices architecture
- Service isolation
- Containerized deployment
- Scalable backend design

---

## Notes

- Ensure Docker is installed and running
- Ports 8080–8082 must be available
- First run may take longer due to image build

---

## Author

Firlyansyah Putra Pratama
