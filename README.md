## Overview ℹ️

This project is a Java Spring application designed to demonstrate the integration of external services via REST APIs, along with persistent data storage using Spring Data JPA. The application provides an endpoint to fetch jokes from an external service and stores them locally in a database.

## Purpose of Local Development 🚀

### Why Local Development?
- **Performance and Flexibility**:
  - Developing a Proof of Concept (POC) locally allows for faster iterations, testing, and debugging. 🚀
  - It avoids dependencies on external resources and internet connectivity issues. 🌐

- **Integration with External Services**:
  - The project integrates with a real-world API to showcase interaction with external systems, adding practical value and complexity to the POC. 🛠️

- **Data Persistence**:
  - Data fetched from the external service is stored locally using Spring Data JPA to simulate business logic involving data storage. 🗃️

## Components 🛠️

- **Controller**:
  - Provides a single endpoint for fetching jokes and interacting with external services.

- **JokeHistoryEntry**:
  - Entity class to persist joke and user information in the database.

- **JokeRepository**:
  - Interface extending Spring Data JPA's repository capabilities for CRUD operations.

## Dependencies 📦

- **Spring Data JPA**: Simplifies data access and manipulation with relational databases.
- **OpenFeign**: Declarative REST client for integrating with external APIs.
- **Spring Boot Validation**: Ensures incoming request parameters adhere to defined constraints.
- **Lombok**: Reduces boilerplate code by auto-generating getters, setters, and constructors.

## Testing Strategy 🧪

### Controller Tests:
- MockMvc setup to simulate HTTP requests and responses.
- Mocked external service responses using JSON files to ensure deterministic testing.
- Validates endpoint functionality, request validation, external service interaction, and database operations.

## Tools Used 🛠️

- **Swagger**: Provides a UI for testing API endpoints.
- **H2 Database**: In-memory database for development and testing, resets on application restart for consistent test runs.

## Considerations 🤔

- **Realistic Testing vs. Mocking**:
  - Mocking external dependencies allows isolated testing but may not cover all real-world scenarios.
  - Ensuring test scenarios are robust and reflect production conditions is crucial for reliable testing.

## Future Implementations 🚀

- Integration into production environment or Virtual Desktop after validating the POC locally.
- Enhancements based on performance metrics and real-world usage scenarios.

