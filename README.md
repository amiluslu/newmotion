
## ℹ️ About Project

This is an OpenAPI system to make quality assurance for an application which is related with backend. Example API has been published here:

    https://swapi.dev/api/

Goal of project is that creating test automation scenarios and building a scalable framework for automation using Java & Rest-Assured with DevOps using Gitlab.
Total 36 api scenarios included in project.

## Using Technologies

- Java 11
- Maven
- JUnit5 with Data Driven Testing and Test Framework
- RestAssured for Api Testing
- SpringBoot
- Gitlab for Continuous Integration
- Jacoco for measuring Test Coverage
- Extent Report for Reporting Test Automation Results

## 🚀 How to use

If you have Java 11 and Maven this project runs easily in one command.
- Clone the project then run in locally, below command;

    - mvn clean install
- If you want to run with pipeline using Gitlab, click below link;

    - https://gitlab.com/amilus/newmotion/-/pipelines
    - You can check the automation results report and test coverage report in `ARTIFACTS` tab. 

## 🗒️ Additional Informations

This project is built by SpringBoot. That means, you will see an API testing framework which is flexible C&R(Clone & Run) for any tools. You can get detail informations in below:

    . application.properties ---> URL or SpringBoot settings for framework initialization.
    . extent.properties ---> configurations to keep tests results historically. In `/reports` directory you can reach the test automation results report after execution finished.
    . In `/reports/jacoco/index.html` file will show you the test coverage results.
    . Gitlab is integrated for Continous Integration. In addition, you can see test automation results report and Jacoco test coverage report in <b>Gitlab Pipeline Test Step -> Browse tab</b>.
    . Each endpoint has own TestClass.
    . JavaDoc used in classes for documentation, also README contains detailed information about project.

## 📝  Bonus
- Data Driven technique added. First test of each class that means, getting information with ID's using this technique.  

  - In classes check, `ParameterizedTest` annotations for Data-Driven tests.
- ExtentReport tool added for detailed logs and keeping automation results historically. Furthermore, it generates user-friendly HTML reports using tagging and statistical data for tests.
- Gitlab integrated for CI pipeline.
- Jacoco Test Coverage tool integrated with project, in order to see coverage results and reports.
- Find Bugs You can check below section.

## 🕷️ Bugs & Faults (Defects)
There are 13 bugs found in the system. You can get explanations of each in below;

1) SwapApi documentation says that, each resources have their schema endpoints. However, resource schema's do not exists. Getting Resource Schema is failed.. Response status code should return 200 and schema details, but endpoint `/schema` returns 404. That means, <b>total 6 bugs</b> for each resource.
2) Vehicle Identification is not done properly. Namely, VehicleID -> 1 returns 404 status code. However, VehicleID -> 4 returns 200. In response, VehicleID informations are not sent back to user. <b>1 bug</b> for this manner.
3) If the data, you are looking for is not found in the sources, the system should return a 404 error code. However, system returns 200 but empty array. 
In standarts, this is a bug. Depends on the business logic, this can be a fault or defect. These are <b>4 bugs</b>, test names are below;

   1) testSearchNonExistingSpeciesByName() 
   2) testSearchNonExistingPersonByName()
   3) testSearchNonExistingPlanetsByName()
   4) testSearchNonExistingFilmByTitle()
   
4) SwapApi documentation says that, data comes from all <b>7 (SEVEN)</b> Star Wars Films. However, Count of films returns 6. <b>This is another bug.</b>
5) Starships Identification is not done properly. Namely, StarshipID -> 1 returns 404 status code. However, StarshipID -> 2 returns 200. In response, StarshipID informations not sent back to user. <b>1 bug </b>for this manner.
