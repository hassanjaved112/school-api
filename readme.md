# School Management REST API

**Important**: Please find the `application.properties.example` file in the root folder.  
	       Rename it to `application.properties` and add your MySQL credentials before running the project.

A secure, domain-driven Spring Boot application to manage student records, courses, enrollments, and grades — complete with JWT-based admin authentication, async file uploads, business validation, and Swagger UI.

*Only a registered and logged-in School Admin can perform all the operations

---

##  Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Security (JWT)
- Spring Data JPA (MySQL)
- RESTful APIs
- Bean Validation
- Swagger UI (springdoc-openapi)
- Async File Upload
- Postman-tested

---

## Setup Instructions

### 1. Clone the Repository

git clone https://github.com/your-username/school-api.git
cd school-api

### 2. Configure MySQL Database
Create a database in MySQL:

CREATE DATABASE school_api_db;

**Update application.properties:**

\\ spring.datasource.url=jdbc:mysql://localhost:3306/school_api_db
\\ spring.datasource.username=your_username
\\ spring.datasource.password=your_password

### 3. Build and Run the Project

./mvnw spring-boot:run
Swagger UI:

http://localhost:8080/swagger-ui/index.html
 Admin Authentication
 Register a New Admin
use this API to register admin--->  POST /api/auth/register
Sample JSON to register Admin
{
  "username": "admin1",
  "password": "secure123"
}
***Login and Get JWT
POST /api/auth/login

{
  "username": "admin1",
  "password": "secure123"
}
Copy the token from response:

    //example Token: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...

*Use this token for all protected endpoints.*

					**********API Endpoint Summary************

Module	   	Method		Endpoint						Description
Auth		POST		/api/auth/register				Register admin
		POST		/api/auth/login					Login and get JWT
Student		CRUD		/api/students					Create, list, update, delete students
Course		CRUD		/api/courses					Create, list, update, delete courses
Enrollment	POST		/api/enrollments				Enroll a student (with rules)
Grade		POST		/api/grades					Record grade for a student
		PUT		/api/grades/{gradeId}				Update grade (if course not completed)
		GET		/api/grades/summary/{studentId}			Get student's grades + GPA summary
FileUpload	POST		/api/upload/students				Upload a CSV to import student records
		GET		/api/upload/status/{jobId}			Check progress of async import

🗂️ CSV Upload Format (No Header)

   Example File Content:
	[FirstName,LastName,Email
	 Ali,Ahmed,ali@gmail.com
	 Sara,Khan,sara@example.com]

		 **Notes**
Use Postman or Swagger to test all endpoints

Use JWT token for all protected API calls

File upload supports async status polling

Includes data validation and business logic (e.g., grade limits, course prerequisites)

 Contact
Maintained by Hassan Javed

Feel free to connect

GitHub
https://github.com/hassanjaved112

LinkedIn
https://www.linkedin.com/in/hassan-javed-alam/