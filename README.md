# 👨‍💻 HRMAutomationTesting

## 📌 Overview
This is an automation testing project for an HRM website, built using the POM. The project
uses Java, Selenium WebDriver, TestNG, and Maven. It supports parallel execution, integrates Allure
Reports, records video, takes screenshots on failure, sends test result emails, and writes detailed logs.
The project also uses Excel files to manage test data and .properties files for configuration.

## 💡 Dependencies

| Library        | Description                                                                 |
|----------------|-----------------------------------------------------------------------------|
| Selenium       | Automates browser actions for UI testing                                    |
| TestNG         | Framework for managing test cases and test suites                           |
| Allure         | Interactive and visual test reports                                         |
| Apache POI     | Read/write Excel files for test data or results                             |
| Log4j          | Logging framework for debugging and tracking                                |
| Monte Recorder | Record screen during test execution                                         |
| Jakarta Mail   | Send test result reports via email                                          |
| Maven          | Project build and dependency management                                     |

## ➡ Features for testing
- **Login**
  - Login with valid registered account
  - Login with invalid username
  - Login with invalid password
- **Client**
  - Add new client
  - Update client's information
  - Delete client
- **Project**
  - Add new project
  - Update project's information
  - Upload file on project
  - Delete project
- **Task**
  - Add new task for project
  - Delete task for project
## ⚙ System requirements
  -  Install [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) **>= 17**
  -  Install [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  -  Instal [Apache Maven](https://maven.apache.org/) **>= 3.9.11**
  -  Install [Allure Framework](https://github.com/allure-framework/allure2/releases) -> This framework for generating report
### ==> Open CMD and run to check if install successfull
 ![image](https://github.com/user-attachments/assets/4fef6f20-415d-494e-b9a8-4dffa260fffb)
 ![image](https://github.com/user-attachments/assets/8d540300-89ff-4d97-91d0-83422968eed3)
 
 ![image](https://github.com/user-attachments/assets/9c0759e6-3458-4eba-bd9d-f7cfd46a81c4)
 
## 🚀 How to run project
1. **Clone this project**
2. **Open project with IntelliJ IDEA**
3. **Run parrallel testcases**
  - Testcase in Suite XML (src/test/resources/suites/yourSuite.xml) is setup in the pom.xml file
![image](https://github.com/user-attachments/assets/9de36ab1-cc8a-4cf1-8ff2-343f069f3839)
  - Build and run test: mvn clean test
![image](https://github.com/user-attachments/assets/10149f1e-1d3d-49fd-9c87-d29ba256f191)
  - View allure report in temporary: allure serve target/allure-results


    







 

    
