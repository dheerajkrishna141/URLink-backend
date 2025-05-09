# URLink Backend

## Overview
The URLink Backend is the core application for [URLink](https://github.com/dheerajkrishna141/URLink-frontend). This Spring Boot application offers RESTful APIs to shorten URLs with user-specific aliases, facilitating redirection to the original URLs. It also includes features such as user authentication, role-based authorization, and Cross-Origin Resource Sharing (CORS) to ensure secure access to user information.

## Installation

### Prerequisites

- Java 11 or higher
- Maven
- MySQL

### Setup

1. Clone the repository:
   
   ```bash
   git clone https://github.com/dheerajkrishna141/URLink-backend.git
2. Navigate to the project directory:
   
   ```bash
   cd urlshortener-backend
3. Configure the database connection in
   
   ```src/main/resources/application.properties:```

   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
4. Build the project:
   ```bash
   mvn clean install
5. Run the project:
   ```bash
   mvn spring-boot:run



## Documentation
For detailed API documentation and usage instructions, visit [ URLink Backend Documentation.](https://dheerajkrishna141.github.io/URLink-backend/api-docs.html) 
