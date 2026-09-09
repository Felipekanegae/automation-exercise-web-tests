# Automation Exercise - Web Test Automation

Web test automation project developed for portfolio purposes using Java, Selenium WebDriver, Cucumber, and the Page Object Model pattern.

The project automates functional test scenarios on the Automation Exercise website, covering login, contact form, product search, shopping cart, product reviews, and checkout validation.

## Technologies

- Java 11
- Selenium WebDriver
- Cucumber
- JUnit Platform
- TestNG Assertions
- Maven
- WebDriverManager
- Apache POI
- IntelliJ IDEA
- Git & GitHub

## Test Automation Approach

The project uses:

- BDD scenarios written in Gherkin
- Page Object Model for separating page interactions from test steps
- PageFactory for element initialization
- Explicit waits with WebDriverWait
- External test data stored in Excel files
- Apache POI for reading test data
- Cucumber hooks for browser setup and teardown
- Scenario tags for identifying test cases

## Automated Scenarios

### Login

- Login with valid credentials
- Successful logout
- Login with invalid credentials
- Registration attempt using an email already in use

### Contact

- Submit a message through the Contact Us form

### Products

- View product details
- Search for a product
- Add multiple products to the cart
- Verify cart contents after login
- Remove a product from the cart
- Submit a product review
- Validate delivery and billing addresses during checkout

## Project Structure

```text
src
├── main
│   └── java
│       ├── AutomationExercise
│       │   ├── Contact
│       │   ├── Login
│       │   └── Products
│       └── testData
│
└── test
    ├── java
    │   └── AutomationExercise
    │       ├── Contact
    │       ├── Login
    │       └── Products
    │
    └── resources
        ├── features
        │   ├── Contact
        │   ├── Login
        │   └── Products
        └── massa
```

## Test Data

Test data is stored in an Excel file located at:

```text
src/test/resources/massa/Massa/automationExercise.xlsx
```

Each Cucumber scenario uses a test case tag, such as:

```gherkin
@CT1
Scenario: Login with valid credentials
```

The scenario tag is used to identify and load the corresponding test data from the Excel file.

## Example Scenario

```gherkin
@CT1
Scenario: Login with valid credentials
  Given I am on the login page
  When I enter a valid email and password
  Then the user should be logged in successfully
```

## Running the Tests

### Prerequisites

Make sure the following tools are installed:

- Java 11 or later
- Maven
- Google Chrome

### Run with Maven

From the project root directory, run:

```bash
mvn test
```

The browser driver is managed automatically by WebDriverManager.

## Test Design

The automation follows separation of responsibilities:

- **Feature files** describe test behavior using Gherkin.
- **Step Definitions** connect Gherkin steps to automation code.
- **Page Objects** contain page elements, actions, and validations.
- **ExcelTestData** handles external test data.
- **Runners** define the Cucumber test execution.

## Author

**Felipe Kanegae**

QA Automation Engineer

GitHub: GitHub: [Felipekanegae](https://github.com/Felipekanegae)