# API_Automation_RestAssured

This project shows some API testing and testing strategies with Rest Assured

1. API Example

- API : https://restful-booker.herokuapp.com/
- API Documentation: https://restful-booker.herokuapp.com/apidoc/index.html

---

2. Test Environment
---
- Java 8 JDK 
- Development IDE – the "Intellij IDEA Community Edition" tool was used.

---

3. API Project Structure 
---

- Package : test/java/com.automation.api
---

- [x] Contract_Tests 

Test scenario for API contract validation using Json Schema Validator.


- [x] EndPoint_Tests

Test scenarios for endpoint validation.

Notes: 

-  Request and Response specifications used to specify response and request patterns.
-  Data Provider – Some scenarios use this function to operate with different data sets

- [x] Scenarios Tests

End-to-end testing using endpoints to simulate user scenarios when using the API.

- [x] Pojo

Model class to mapping objects.

- [x] Factory

Data Factory to control the data that will be used in the tests.

Notes:

- ObjectMapper class and how to serialize Java objects into JSON and deserialize JSON string into Java objects. 


- [x] Config

The Configuration class reads information from the resources/properties/test.properties file with the ConfigurationManager using the Owner library

---

- Package : test/java/resources

---

- [x] Properties

The properties file to configure the API.

- [x] RequestBody

A json file template to be used to add data to data factories.

- [x] Schemas

A schema folder with JSON schemas for Contract Testing.

---
4. Libraries
---

- [x] RestAssured library to test REST APIs
- [x] Owner to manage the property files
- [x] JUnit 4 to support the test creation
- [x] ObjectMapper
- [x] Data Provider

---

5. Patterns Applied

---

- [x] Test Data Factory
- [x] Data Provider
- [x] Builder
- [x] Request and Response Specification

---

---

6. API Testing Strategy

---

As part of the testing strategy, some points from the POISED heuristic were applied to this project

- POISED Heuristic by Amber Race (Parameters, Output, Interop, Security, Errors and Data)

---

