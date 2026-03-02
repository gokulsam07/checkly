# Checkly Test Automation Framework

This repository contains a unified test automation framework for end-to-end testing of both the UI and API layers of the Checkly application. It is built with a robust stack of modern automation tools.

## ✨ Features

*   **UI Automation:** Uses [Selenide](https://selenide.org/) for concise and stable browser-based tests.
*   **API Automation:** Uses [REST Assured](https://rest-assured.io/) for testing RESTful services.
*   **Test Runner:** Powered by [TestNG](https://testng.org/doc/) to allow for flexible test configuration and execution.
*   **Reporting:** Generates detailed and interactive [Allure Reports](https://allurereport.org/).
*   **Build Automation:** Managed by [Apache Maven](https://maven.apache.org/).
*   **CI/CD:** Integrated with GitHub Actions for continuous testing.

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your system:

*   **Java Development Kit (JDK):** Version 17
*   **Apache Maven:** Version 3.8.x or higher

---

## 🚀 Getting Started

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/gokulsam07/checkly.git
    cd checkly
    ```

2.  **Install dependencies:**
    Maven will automatically download all the required dependencies when you run your first test.

---

## ⚙️ Configuration

Test execution can be configured via properties files located in `src/test/resources`.

*   `selenide.properties`: Contains settings for the Selenide framework (e.g., browser, timeouts).
*   `login.properties`: Contains credentials for running tests.

**Note:** For local runs, you can modify `login.properties` directly. However, for CI/CD, credentials are provided securely via GitHub Actions Secrets, which is the recommended practice.

---

## 📁 Project Structure

```
checkly/
├── .github/workflows/         # GitHub Actions CI/CD pipelines
│   └── selenide.yml
├── src/main/java/
│   ├── pages/                 # Page Object Models for UI tests
│   ├── rest/core/             # Core REST Assured implementation
│   └── ui/core/               # UI-specific utilities
├── src/test/java/
│   ├── api/                   # API test classes
│   ├── ui/                    # UI test classes
│   └── test/utils/            # Common test helpers
├── src/test/resources/
│   ├── login.properties       # Credentials for testing
│   ├── selenide.properties    # Selenide configuration
│   └── data/api/              # JSON request bodies for API tests
├── allure-results/            # Raw Allure test results (generated after test run)
├── checkly-api.xml            # TestNG suite for API tests
├── checkly-ui.xml             # TestNG suite for UI tests
└── pom.xml                    # Maven Project Object Model
```

---

## ▶️ How to Run Tests

All tests are executed using Maven from the command line. You can run the entire suite or target specific test types (UI, API) or groups.

### Run All UI Tests

This command executes the tests defined in the `checkly-ui.xml` TestNG suite.

```bash
mvn test -DsuiteXmlFiles=checkly-ui.xml
```

To run UI tests in headless mode:

```bash
mvn test -DsuiteXmlFiles=checkly-ui.xml -Dselenide.headless=true
```

### Run All API Tests

This command executes the tests defined in the `checkly-api.xml` TestNG suite.

```bash
mvn test -DsuiteXmlFiles=checkly-api.xml
```

### Run Specific Test Groups

You can run specific groups of tests (as defined in your test classes with `@Test(groups = {"...})`) by using the `-Dgroups` property.

```bash
# Example: Run tests tagged as 'smoke' from the UI suite
mvn test -DsuiteXmlFiles=checkly-ui.xml -Dgroups="smoke"

# Example: Run tests tagged as 'regression' from the API suite
mvn test -DsuiteXmlFiles=checkly-api.xml -Dgroups="regression"
```

---

## 📊 Viewing the Allure Report

After running tests, Allure results are generated in the `target/allure-results` directory.

To generate and view the HTML report locally, run the following command:

```bash
mvn allure:report
```

This will generate the report in the `target/site/allure-maven-plugin` directory. To open it in your browser, run:

```bash
mvn allure:serve
```

---

## 🤖 CI/CD Pipeline

This project uses **GitHub Actions** for continuous integration. The workflow is defined in `.github/workflows/selenide.yml`.

### How it Works

*   **Manual Trigger:** The workflow is triggered manually, allowing you to choose which suite to run (`ui` or `api`).
*   **Test Execution:** It checks out the code, sets up JDK 17, and runs the selected test suite using Maven.
*   **Report Generation:** After the test run, it generates the Allure report.
*   **Report Deployment:** The final HTML report is automatically deployed to GitHub Pages. You can find a link to the report in the "Summary" of the workflow run.

### Triggering the Workflow

1.  Navigate to the **Actions** tab in the GitHub repository.
2.  Select the **checkly-ci** workflow from the sidebar.
3.  Click the **Run workflow** dropdown.
4.  Choose the `test-suite` (`ui` or `api`) you want to run.
5.  Click the **Run workflow** button.
