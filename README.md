# Repsut - Recipe Management App

- Repsut is a powerful recipe management application designed to help users organize, store, and manage their favorite recipes efficiently. This application is built using JHipster 8.7.1, leveraging modern technologies for a seamless development and user experience.

- For detailed JHipster documentation, visit: JHipster 8.7.1 Documentation.

## Tech Stack

##### Repsut utilizes the following technologies:

- Backend: Spring Boot, JHipster

- Frontend: React, Redux

- Database: MariaDB

- Build Tools: Maven, Webpack

- Containerization: Docker, Docker Compose

- Testing: Jest, Spring Boot Tests

## Project Structure

##### JHipster generates a well-structured project layout:

- /src/main - Contains Java and React source code
- /src/test - Unit and integration test files
- /src/main/docker - Docker configurations
- .yo-rc.json - JHipster configuration file
- .jhipster/\*.json - Entity configuration files
- package.json - Node.js dependencies and scripts
- npmw - Wrapper for npm to ensure consistent dependency management

## Testing

##### Run backend tests:

- ./mvnw verify

##### Run frontend tests:

- ./npmw test

Continuous Integration

Currently, there is no CI/CD configured for Repsut. Future updates may include integration with platforms like GitHub Actions, Jenkins, or GitLab.

For further information, visit the official JHipster documentation: JHipster Documentation.
