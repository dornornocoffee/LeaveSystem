# Leave System Project

This project is a Leave Management System consisting of three main components:
1. **Database & Dependencies** - Managed using Docker Compose.
2. **Backend** - A Spring Boot application located in `LeaveSystem\leave-system`.
3. **Frontend** - An Angular application located in `LeaveSystem\leave-system-fe`.

## How to Run the Project

### 1 Start Docker Services
Run the following command to start the database and required dependencies:
```sh
docker-compose up -d
```

### 2️ Run the Spring Boot Backend
Navigate to the backend folder and start the application:
```sh
cd LeaveSystem/leave-system
mvnw.cmd spring-boot:run 
```
This will start the Spring Boot application on **`http://localhost:8080`**.

### 3️ Run the Angular Frontend
Navigate to the frontend folder and start the Angular application:
```sh
cd LeaveSystem/leave-system-fe
npm install 
npm start
```
The Angular app will be available at **`http://localhost:4200`**.

---
