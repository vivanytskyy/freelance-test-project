# Final study project in the Hillel QA Automation course.

## The technical assignment

### Task 1
*Develop the API tests for the following API endpoints:*
- auth-controller;
- user-controller;
- job-controller;
- comment-controller;
- image-controller (optional).<br>

You can use any http-client.<br>

*Links:*
- [REST API](https://freelance.lsrv.in.ua/api/);
- [Swagger-UI](https://freelance.lsrv.in.ua/swagger-ui/index.html);
- [Swagger-doc](https://freelance.lsrv.in.ua/v3/api-docs/).

### Task 2
*Develop UI tests for Registration and Login functionalities for the following use cases:*
- User can log in, go to the Profile page, and change his profile data (to check if new data is shown on the page correctly);
- User can log in, go to the Profile page, create a new advertisement (to check if a new ad is shown in the My Jobs section);
- User can log in, choose an advertisement on the main page, view its details, and leave a comment;
- User can log in, go to the Profile page, view the list of his advertisements and the number of comments on them, and delete his advertisements.<br>

You can use the API for data creation in the application within the test scenarios.<br> 
You can use either tool (Selenium or Selenide) for UI testing in your decision.<br>
Tests have to use methods of the Page Object classes.<br>

*Link:*
- [UI part](https://freelance.lsrv.in.ua/)

### Task 3
Add the report generation for completed tests with screenshots of the failed UI tests.

## The following technologies and libraries were applied in this project:
- [Java SE 17](https://docs.oracle.com/en/java/javase/17/);
- [Selenide](https://selenide.org/documentation.html);
- [TestNG](https://testng.org/doc/);
- [Apache Maven](https://maven.apache.org/);
- [Lombok](https://projectlombok.org/).