This implementation is for take home assignment received from Geico as part of the interviewing process for candidates.
It has been built with Java11 and uses Maven for building.
1. $ mvn clean verify to build.
2. $ mvn clean spring-boot:run to run locally on port 8080
3. List all Tasks $ curl -v localhost:8080/tasks/
4. Delete a task $ curl -X DELETE localhost:8080/tasks/<id>
5. Find a task by ID $  curl -v localhost:8080/tasks/<id>
6. Create a task $ curl -X POST localhost:8080/tasks -H 'Content-type:application/json' -d '{"name": "Sample Task", "dueLocalDate": "2022-07-07"}'
7. Update a task $ curl -X PUT localhost:8080/tasks/<id> -H 'Content-type:application/json' -d '{"name": "Sample Task2", "dueLocalDate": "2022-06-07"}}'

Sample payloads available in tests.

A little bit about the project architecture.
This project uses Spring Boot with an embedded H2 database using JPA for ORM mapping.
It is a typical layered software design with a controller layer, a service layer and a repository layer.
The model is represented in domain. Spring provides dependency injection capabilities and several useful framework level
capabilities that I have used to create this project and test it albeit
in a bit of a haste :). Which is why it is rough on the edges especially error cases.

Next steps would have been for me to:
1. Test with a live database like postGres or MS SQL Server
2. Introduce Changeset management with Liquibase or Flyway
3. More tests and make it robust! I would have like to add more tests and do things like testing for concurrency etc. Perhaps with a CountdownLatch.
4. Spare thought cycles about how this would have been scaled. Partitioning data etc.

Looking forward to discussing more about this in the coming days.



Here is the original text as copied from Doc received in mail:
Take-home Coding Assignment
Requirement:
Candidate should have the appropriate IDE installed locally.
For .Net position: Visual Studio 2019 or later (Windows), Visual Studio for Mac 2019 or later (MacOS)
For Java position: Eclipse V4 or later, or NetBeans V13 or later
For Python: Visual Studio Code latest version or PyCharm latest version
Coding Assignment - 1
Develop a very simple RESTful application that demonstrates the ability to
Add new Tasks
Update Tasks that already exist. 
Overall Requirements:
Visual Studio 2019 or Visual Studio 2022 should be used to build the application
The application should be written as an ASP.Net Core Web API application (ASP.Net Core 3.1/5/OR 6)
If a database is used, please use SQL Server 2017 or 2019
Unit tests should be used to run the application (create or update)
Deploy the Web API to Azure platform by using a free account or free tier services. The deployment should be able to support the demonstration of the scenarios like adding a task or updating an existing task.

Task Requirements:
When creating a Task, the Due Date cannot be in the past
The system should not have more than 100 High Priority tasks which have the same due date and are not finished yet at any time
Domain should include the following:
Id
Name
Description
Due Date
Start Date
End Date
Priority (High, Middle, Low)
Status (New, In Progress, Finished)

Concepts to Consider:
Test Driven Development (TDD)
Dependency Injection (DI)
Domain Driven Design (DDD)

Submitting the Application:
Within 48 hours, please send a ZIP file back to us at UWSExternalHiringIntake@geico.com  which should contain the source code.  Please do not include object or bin folders.
Please include a README file with the application that explains how to compile, configure, and run the application – including how to initialize the DB with SQL scripts if used.
Once we receive the zip file and unzip, we should be able to open the solution with Visual Studio, compile and run unit tests.

