# 🚀 CollabHub – Developer Collaboration Platform

CollabHub is a backend-powered platform that connects developers with collaborative project opportunities. It allows project owners to post projects and manage incoming collaboration requests, while developers can apply, track their requests, and join meaningful projects.

---

## ✅ Features

- 🔐 **JWT Authentication** with access token expiry
- 👤 **Developer Profile Management**
- 📁 **Project CRUD APIs** for owners
- 🤝 **Collaboration Request Flow**
- 🌐 **Elasticsearch-based Developer Search**

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **MongoDB**
- **Elasticsearch**
- **JWT for authentication**
- **Docker**

---

## 📋 Prerequisites

Before running CollabHub, ensure you have the following installed on your system:

- **Java 17** or higher
- **Maven 3.6+** for dependency management and building
- **Docker** and **Docker Compose** for running Elasticsearch
- **MongoDB** (local installation or Docker container)
- **Git** for cloning the repository

### Verify Prerequisites
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Docker version
docker --version
docker-compose --version

# Check MongoDB (if running locally)
mongosh --version
```

---

## 🔧 Environment Variables

Create a `.env` file in the root directory or set the following environment variables in your system:

| Variable | Description | Default/Example | Required |
|----------|-------------|-----------------|----------|
| `DB_HOST_NAME` | MongoDB host address | `localhost` | ✅ |
| `DB_PORT` | MongoDB port number | `27017` | ✅ |
| `DB_NAME` | Database name for the application | `collabhub_db` | ✅ |
| `ELASTIC_SEARCH_URIS` | Elasticsearch connection URL | `http://localhost:9200` | ✅ |
| `JWT_SECRET_KEY` | Secret key for JWT token signing | `your-secret-key-here` | ✅ |
| `JWT_ACC_TOKEN_EXPIRY_IN_MS` | Access token expiry time in milliseconds | `900000` (15 minutes) | ✅ |

### Setting Environment Variables

#### Option 1: Using .env file (Recommended)
Create a `.env` file in the project root:
```bash
DB_HOST_NAME=localhost
DB_PORT=27017
DB_NAME=collabhub_db
ELASTIC_SEARCH_URIS=http://localhost:9200
JWT_SECRET_KEY=your-super-secret-jwt-key-change-this-in-production
JWT_ACC_TOKEN_EXPIRY_IN_MS=900000
```

#### Option 2: Export in terminal (Linux/macOS)
```bash
export DB_HOST_NAME=localhost
export DB_PORT=27017
export DB_NAME=collabhub_db
export ELASTIC_SEARCH_URIS=http://localhost:9200
export JWT_SECRET_KEY=your-super-secret-jwt-key-change-this-in-production
export JWT_ACC_TOKEN_EXPIRY_IN_MS=900000
```

#### Option 3: Set in Windows Command Prompt
```cmd
set DB_HOST_NAME=localhost
set DB_PORT=27017
set DB_NAME=collabhub_db
set ELASTIC_SEARCH_URIS=http://localhost:9200
set JWT_SECRET_KEY=your-super-secret-jwt-key-change-this-in-production
set JWT_ACC_TOKEN_EXPIRY_IN_MS=900000
```

> ⚠️ **Security Note**: Never commit your `.env` file or expose your `JWT_SECRET_KEY` in public repositories. Add `.env` to your `.gitignore` file.

---

### 📦 Clone and Setup

#### 1. Clone the repository

```bash
git clone https://github.com/mannam11/collabhub.git
cd collabhub
```

#### 2. Set up your environment variables (see section above)

#### 3. Run ElasticSearch instance using docker
```bash
docker-compose up -d
```

##### Verify ElasticSearch is running
```bash
curl http://localhost:9200
```

#### 4. Start the application
```bash
./mvnw spring-boot:run
# or if you have Maven installed globally
mvn spring-boot:run
```

#### 5. Access the application