# Transactions API

RESTful service to store and query transactions.

## Requirements

- Java 17
- Maven
- Docker

## Run locally

```bash
./mvnw spring-boot:run
```

## Run with Docker

```bash
docker build -t transactions-api .
docker run -p 8080:8080 transactions-api
```

## Run tests

```bash
./mvnw test
```

## Endpoints

### PUT /transactions/{id}
Creates a new transaction.

**Body:**
```json
{
  "amount": 5000,
  "type": "cars",
  "parent_id": 10
}
```

**Response:**
```json
{ "status": "ok" }
```

### GET /transactions/types/{type}
Returns all transaction IDs for the given type.

**Response:**
```json
[10, 11]
```

### GET /transactions/sum/{id}
Returns the total sum of the transaction and all its children recursively.

**Response:**
```json
{ "sum": 20000.0 }
```

## Architecture

- **Controller** - handles HTTP requests
- **Service** - business logic and validations
- **Repository** - in-memory storage using ConcurrentHashMap