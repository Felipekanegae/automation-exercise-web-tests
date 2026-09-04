# Automation Exercise - Web Test Automation

Web test automation project developed for the [Automation Exercise](https://automationexercise.com/) website.

The goal of this project is to practice and demonstrate automated testing concepts using Java, Selenium WebDriver and Cucumber, applying good practices for test organization and maintainability.

## Technologies

- Java
- Selenium WebDriver
- Cucumber
- JUnit
- Maven
- Apache POI
- IntelliJ IDEA
- Git & GitHub

## Project Structure

```text
src
├── main
│   └── java
│       ├── AutomationExercise
│       │   ├── Contact
│       │   └── Login
│       └── massa
│
└── test
    ├── java
    │   └── AutomationExercise
    └── resources
        ├── features
        │   ├── Contact
        │   └── Login
        └── massa
```

The project separates page interactions, test steps, test scenarios and test data in order to keep the automation organized and maintainable.

## Automated Scenarios

### Login and User Registration

- Create a new user
- Logout
- Login with invalid credentials
- Register with an existing email

### Contact

- Submit a contact/support request

More scenarios will be added as the project evolves.

## BDD

Test scenarios are written using Gherkin syntax with Cucumber.

Example:

```gherkin
Feature: Login

  Scenario: Login with invalid credentials
    Given the user accesses the login page
    When the user enters invalid credentials
    Then an authentication error message should be displayed
```

## Test Data

Test data is stored separately from the test implementation and can be read from external files using Apache POI.

This approach helps keep test data independent from the automation code.

## Running the Tests

### Prerequisites

Make sure the following tools are installed:

- Java
- Maven
- Google Chrome

Clone the repository:

```bash
git clone https://github.com/Felipekanegae/automation-exercise-web-tests.git
```

Navigate to the project directory:

```bash
cd automation-exercise-web-tests
```

Run the automated tests:

```bash
mvn test
```

## Website Under Test

Automation Exercise:

https://automationexercise.com/

The website provides practice scenarios for web and API test automation.

## Author

**Felipe Kanegae**

QA Automation Engineer

GitHub: [Felipekanegae](https://github.com/Felipekanegae)
