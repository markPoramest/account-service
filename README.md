# Account Service

This project is a Spring Boot application developed using Java 17, providing APIs for managing user accounts. It includes endpoints for creating accounts, retrieving accounts, searching accounts by first name with pagination, and fetching individual accounts by their IDs.

## APIs

### Create Account
- Endpoint: `POST /account`
- Description: This API creates a new account based on the provided account input. It sends a request to the crypto service for encrypting the ID card number using RestTemplate.
- Request Body: AccountInput
- Response: Account
- HTTP Status: 201 Created

### Get All Accounts
- Endpoint: `GET /account`
- Description: Retrieves a list of all accounts.
- Response: List of Account
- HTTP Status: 200 OK

### Search Accounts by First Name
- Endpoint: `GET /account/search`
- Description: Searches accounts by first name with pagination support.
- Query Parameters:
    - firstName: First name to search for
    - pageable: Pageable parameters for pagination
- Response: Page of Account
- HTTP Status: 200 OK

### Get Account by ID
- Endpoint: `GET /account/{id}`
- Description: Retrieves an account by its ID. It sends a request to the crypto service for decrypting the ID card number using RestTemplate.
- Path Variable: id (Long)
- Response: Account
- HTTP Status: 200 OK

## Additional Features

- **Logging with Google Cloud Format**: The application utilizes logging in the Google Cloud format.
- **Actuator Endpoints**:
    - `/health`: Exposes health information.
    - `/info`: Exposes application information.
    - `/metrics`: Exposes metrics information.
- **Unit Testing with JUnit and Mockito**: Includes unit tests for testing the functionality of the service components.

## Running the Application

1. Ensure you have Java 17 installed on your system.
2. Clone the repository.
3. Navigate to the project directory.
4. Build the project using Maven or Gradle.
5. Run the application.
6. Access the APIs using the provided endpoints.

## Dependencies

- Spring Boot 3
- Java 17
- JUnit
- Mockito
- Google Cloud Logging
- Spring Boot Actuator

## Configuration

- Logging configuration for Google Cloud Format.
- Actuator endpoints configuration.

## Testing

Unit tests are included to ensure the functionality of service components. Mockito is used for mocking dependencies.
