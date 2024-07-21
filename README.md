# checkly

This is a unified framework built for testing both UI & API layer using Selenide, Rest Assured, TestNG

### Prerequisites

* Java
* Selenide 
* Rest Assured
* TestNG

### Installation

1. Clone the repository: `git clone https://github.com/gokulsam07/checkly.git`

### Project Structue

```
checkly/                              # root dir
├── src/main/java                                              
     ├── pages/                       # page objects for all UI pages                    
     ├── rest.core/                   # rest implementation to for common http methods
     └── ui.core/                     # utility methods for ui layer                 
├── src/test/java
     ├── checkly.tests.api.**         # api tests                 
     ├── checkly.tests.ui.**          # ui tests
     └── test.utils                   # helper methods for tests                                   
├── pom.xml                           # dependency management
├── checkly-api.xml                   # xml file for api tests
├── checkly.ui.xml                    # xml file to test ui 
└── testng.xml                        # combined ui & api test

```

### Test Runs

* How to run the test? `mvn test path\to\testng.xml`






