# 🏦 Banking Microservices System

## 📝 Project Overview
A production-grade banking system built with a microservices architecture featuring three independent services for accounts, cards, and loans management. The system implements centralized configuration management, dynamic configuration refresh without restart, Docker containerization, and cloud-ready deployment.

### Key Features
* ✅ **3 Independent Microservices** (Accounts, Cards, Loans)
* ✅ **Centralized Configuration** with Spring Cloud Config Server
* ✅ **Dynamic Configuration Refresh** via RabbitMQ (Zero Downtime)
* ✅ **Docker Containerization** & Orchestration
* ✅ **Environment-Specific Profiles** (dev/qa/prod)
* ✅ **Health Checks** & Production Monitoring
* ✅ **Per-Service Database Isolation**
* ✅ **RESTful APIs** with Full CRUD Operations

---

## 🏗️ Architecture Diagram
The layout below illustrates how the microservices, centralized configuration server, and RabbitMQ message broker coordinate inside the custom isolated network.

![System Architecture Diagram](https://github.com/user-attachments/assets/88483bd8-f8d7-405b-b070-36563365ee72)

---

## 🔄 Configuration Management Flow
This sequence flow highlights how a property modification pushed to the remote GitHub configurations repository propagates to all services seamlessly using Spring Cloud Bus.

![Configuration Management Flow](https://github.com/user-attachments/assets/b1046498-86f7-4985-ac23-9b6e2dd4fc5d)

---

# Required installations
- Java 17
- Docker Desktop (for containerized deployment)
- Maven 3.9+ (for local development)
- Git 

Clone the Repository
 # Clone main code repository
git clone https://github.com/AffanShaikAbdulla/BankingApplication.git
cd BankingApplication

# Clone configuration repository (separate)
git clone https://github.com/AffanShaikAbdulla/banking-config.git 
🐳 Docker Deployment (Recommended) 
# Navigate to project root
cd /f/Microservices/Section02
# Start all containers
docker-compose up -d
# Check container status
docker-compose ps
# View logs
docker-compose logs -f 

Stop All Services 
docker-compose down 
### 🐳 Container Ports

| Service | Port(s) | Purpose |
| :--- | :--- | :--- |
| **RabbitMQ** | `5672`, `15672` | Message broker, Management UI |
| **Config Server** | `8071` | Centralized configuration |
| **Accounts** | `8080` | Accounts REST APIs |
| **Loans** | `8090` | Loans REST APIs |
| **Cards** | `9000` | Cards REST APIs | 
## 💻 Local Development (Without Docker)

### 1. Start Config Server First
```bash
cd /f/Microservices/SpringConfig
mvn spring-boot:run 
# Terminal 1 - Accounts Service
cd accounts
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 2 - Cards Service
cd cards
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminal 3 - Loans Service
cd loans
mvn spring-boot:run -Dspring-boot.run.profiles=dev 

## 📡 API Endpoints

### Accounts Service (Port 8080)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/create` | Create new account |
| `GET` | `/api/fetch?mobileNumber=xxx` | Fetch account details |
| `PUT` | `/api/update` | Update account |
| `DELETE` | `/api/delete?mobileNumber=xxx` | Delete account |
| `GET` | `/api/build-info` | Get build version |
| `GET` | `/api/contact-info` | Get contact information |
| `GET` | `/actuator/health` | Health check |

---

### Cards Service (Port 9000)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/create?mobileNumber=xxx` | Issue new card |
| `GET` | `/api/fetch?mobileNumber=xxx` | Fetch card details |
| `PUT` | `/api/update` | Update card |
| `GET` | `/actuator/health` | Health check |

---

### Loans Service (Port 8090)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/create?mobileNumber=xxx` | Apply for loan |
| `GET` | `/api/fetch?mobileNumber=xxx` | Fetch loan details |
| `PUT` | `/api/update` | Update loan |
| `GET` | `/actuator/health` | Health check |
Loans Service (Port 8090)
Method	Endpoint	Description
POST	/api/create?mobileNumber=xxx	Apply for loan
GET	/api/fetch?mobileNumber=xxx	Fetch loan details
PUT	/api/update	Update loan
GET	/actuator/health	Health check 

## 🔧 Configuration Profiles

### Available Profiles

* **dev Profile**
  * **Database:** H2 (in-memory)
  * **Log Level:** `DEBUG`
  * **Use Case:** Local development

* **qa Profile**
  * **Database:** H2 (in-memory)
  * **Log Level:** `INFO`
  * **Use Case:** Testing environment

* **prod Profile**
  * **Database:** MySQL
  * **Log Level:** `WARN`
  * **Use Case:** Production environment

---

### Using Profiles

```bash
# Local development execution
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Docker deployment (via environment variable override)
docker run -e SPRING_PROFILES_ACTIVE=prod shaik/accounts:s6 

Docker Images
Available Infrastructure & Service Images 
# Centralized Configuration Hub
shaik/configserver:s6

# Core Business Application Services
shaik/accounts:s6
shaik/cards:s6
shaik/loans:s6

# Message Broker Communication Layer
rabbitmq:4-management 

Build Container Images Manually 
# Option A: Build standard images using local Dockerfiles
cd accounts && docker build -t shaik/accounts:s6 .

# Option B: Build images directly utilizing Jib Maven plugin compilation
cd accounts && mvn compile jib:dockerBuild 
Environment Variables
Docker Compose Base Configurations
YAML
## 🔧 Configuration Profiles

### Available Profiles

* **dev Profile**
  * **Database:** H2 (in-memory)
  * **Log Level:** `DEBUG`
  * **Use Case:** Local development

* **qa Profile**
  * **Database:** H2 (in-memory)
  * **Log Level:** `INFO`
  * **Use Case:** Testing environment

* **prod Profile**
  * **Database:** MySQL
  * **Log Level:** `WARN`
  * **Use Case:** Production environment

---

### Using Profiles

```bash
# Local development execution
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Docker deployment (via environment variable override)
docker run -e SPRING_PROFILES_ACTIVE=prod shaik/accounts:s6



Select the Import action option from the main layout menu.

Choose and import the routing file: BankingApplication.postman_collection.json.

Choose and import the environment matching file: BankingApplication.postman_environment.json.

Trigger requests to test the live API endpoints!  

Docker Images
Available Infrastructure & Service Images
Bash 
# Centralized Configuration Hub
shaik/configserver:s6

# Core Business Application Services
shaik/accounts:s6
shaik/cards:s6
shaik/loans:s6

# Message Broker Communication Layer
rabbitmq:4-management 

Build Container Images Manually 
# Option A: Build standard images using local Dockerfiles
cd accounts && docker build -t shaik/accounts:s6 .

# Option B: Build images directly utilizing Jib Maven plugin compilation
cd accounts && mvn compile jib:dockerBuild 

Environment Variables
Docker Compose Base Configurations 
# Configuration Server Profile Engine
SPRING_PROFILES_ACTIVE: default

# Microservice Core Infrastructure Topology
SPRING_APPLICATION_NAME: accounts
SPRING_PROFILES_ACTIVE: default
SPRING_CONFIG_IMPORT: "optional:configserver:http://configserver:8071"
SPRING_RABBITMQ_HOST: rabbitmq

Connection Settings Overrides for Production Environments 

# Override local sandbox values with production database server links on startup
docker run -e SPRING_DATASOURCE_URL=jdbc:mysql://prod-db:3306/accounts \
           -e SPRING_DATASOURCE_USERNAME=admin \
           -e SPRING_DATASOURCE_PASSWORD=secret \
           shaik/accounts:s6 

**Testing with Postman**

Import Collection Instructions
1️⃣ Open your Postman client dashboard.

2️⃣ Select the Import action option from the main layout menu.

3️⃣ Choose and import the routing file: BankingApplication.postman_collection.json

4️⃣ Choose and import the environment matching file: BankingApplication.postman_environment.json

5️⃣ Trigger requests to test the live API endpoints!

Sample API Calls 
# 1. Create Account
POST http://localhost:8080/api/create
Content-Type: application/json

{
    "name": "shaik Abdulla",
    "mobileNumber": "9876543210",
    "email": "sk@gmail.com"
}

# 2. Fetch Account Details
GET http://localhost:8080/api/fetch?mobileNumber=9876543210

# 3. Create Card
POST http://localhost:9000/api/create?mobileNumber=9876543210

# 4. Create Loan
POST http://localhost:8090/api/create?mobileNumber=9876543210 

Future Enhancements 
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Service Discovery (Netflix Eureka)
- [ ] Distributed Tracing (Zipkin/Sleuth)
- [ ] Circuit Breaker (Resilience4j)
- [ ] Kubernetes Deployment (Helm Charts)
- [ ] CI/CD Pipeline (GitHub Actions)
- [ ] JWT Authentication & Authorization
- [ ] Swagger/OpenAPI Documentation
- [ ] Centralized Logging (ELK Stack)
- [ ] Prometheus + Grafana Monitoring 

Author
Shaik Abdulla
Email: shaikabdulla0298@gmail.com
GitHub: AffanShaikAbdulla 

 License
This project is for portfolio demonstration purposes. 
🙏 Acknowledgments
Spring Boot & Spring Cloud Documentation

Docker Documentation

Banking domain inspiration from real-world systems


