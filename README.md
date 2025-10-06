# Rial Digital Service Bus

A modular, event-driven **service bus** built with **Spring Boot**, designed for digital Rial integrations.  
This backend system acts as a middleware layer between banking services, payment processors, and external APIs — enabling secure, scalable, and maintainable service orchestration.

---

## ⚙️ Purpose

The Rial Digital Service Bus provides a centralized platform for:

- Routing and transforming service messages  
- Orchestrating banking operations  
- Logging and auditing events  
- Handling retries and fallbacks  
- Managing secure API communication  

---

## ✨ Key Features
- Built with **Java 17 + Spring Boot**  
- Modular service architecture  
- RESTful endpoints for service invocation  
- Central dispatcher for routing messages  
- Retry logic and timeout handling  
- Environment-based configuration with `application.yml`  
- Integrated logging and error tracking  
- Ready for containerization and deployment  

---

## 📁 Project Structure
rial_digital_service_bus/ │── src/ │ ├── main/ │ │ ├── java/com/rialbus/ │ │ │ ├── controller/ # REST endpoints │ │ │ ├── service/ # Business logic │ │ │ ├── dispatcher/ # Message routing │ │ │ ├── model/ # DTOs and entities │ │ │ └── config/ # App configuration │ └── resources/ │ ├── application.yml # Environment config │ └── logback.xml # Logging config │── pom.xml # Maven dependencies

Code

---

## 🚀 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/mehdiarz/rial_digital_service_bus.git
cd rial_digital_service_bus
2. Build the project
bash
mvn clean install
3. Run the application
bash
mvn spring-boot:run
The service will start on http://localhost:8080 by default.

🔌 Example Endpoints
Method	Endpoint	Description
POST	/api/dispatch	Dispatch a service message
GET	/api/health	Health check
GET	/api/logs	View recent logs
...	/api/service/{name}	Invoke specific service
🛡️ Reliability & Security
Input validation with Spring annotations

Retry logic with exponential backoff

Timeout handling for external services

Centralized error logging

Configurable CORS and headers

🧪 Roadmap
Add unit and integration tests with JUnit + Mockito

Integrate with Kafka or RabbitMQ for async messaging

Add Swagger/OpenAPI documentation

Implement circuit breaker pattern with Resilience4j

Add Prometheus metrics and Grafana dashboards

👨‍💻 Author
Developed and maintained by Mehdi Arz

📄 License
This project is licensed under the MIT License.
