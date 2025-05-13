# Bank Management System

A comprehensive Bank Management System built using Java, JDBC, Swing, and MySQL.

## Repository Link
[Bank Management System](https://github.com/Tanishk192/Bank-Management-System)

## Prerequisites
- Java JDK 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Tanishk192/Bank-Management-System.git
   cd Bank-Management-System
   ```

2. **Database Setup**
   - Install MySQL Server if not already installed
   - Create a new database named `bank_management`
   - Update database credentials in `src/main/java/com/banksystem/util/DatabaseConfig.java`:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/bank_management";
     private static final String USER = "your_username";
     private static final String PASSWORD = "your_password";
     ```
   - Run the SQL scripts in `src/main/resources/` directory to set up the database schema

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.banksystem.Main"
   ```

## Features
- User Authentication (Login/Register)
- Account Management
- Transaction Management
- Admin Dashboard
- Account Statements
- Fund Transfers

## Default Admin Credentials
- Username: admin
- Password: admin123

## Default User Credentials
- Username: user
- Password: user123

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── banksystem/
│   │           ├── dao/
│   │           ├── model/
│   │           ├── service/
│   │           ├── ui/
│   │           └── util/
│   └── resources/
│       └── sql/
```

## Contributing
Feel free to fork the repository and submit pull requests for any improvements.

## License
This project is licensed under the MIT License. 