# Banking System Console Application

## Overview

This is a simple Java-based console application that simulates a basic banking system with user registration, login, and account management functionality. It uses **MySQL** as the backend database and JDBC for connectivity.

---

## Features

- **User Registration & Login**
- **Account Creation** for registered users
- **Credit Money** to account with PIN verification
- **Debit Money** from account with sufficient balance and PIN verification
- **Money Transfer** between accounts with PIN verification and transaction atomicity
- **Check Account Balance** with PIN verification

---

## Technologies Used

- **Java 8+**
- **MySQL 8+**
- **JDBC (Java Database Connectivity)**
- Console-based User Interface
- PreparedStatements for SQL queries to prevent SQL Injection

---

## Project Structure

| Class Name      | Description                                      |
|-----------------|-------------------------------------------------|
| `BankingApp`    | Main class to run the application and handle menu navigation |
| `User`          | Handles user registration and login             |
| `Accounts`      | Manages bank account creation and account checks |
| `AccountManager`| Handles transactions: credit, debit, transfer, and balance check |

---

## Setup Instructions

### 1. Database Setup

- Install and configure MySQL.
- Create a database named `banking_system`.
- Create the following tables:

```sql
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE Accounts (
    account_number BIGINT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0,
    security_pin VARCHAR(10) NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email)
);
```

### 2. Update Database Credentials

Update your `BankingApp.java` with your MySQL username and password:

```java
private static final String url = "jdbc:mysql://localhost:3306/banking_system";
private static final String username = "root";
private static final String password = "your_mysql_password";
```

### 3. Add MySQL JDBC Driver to Classpath

Download the [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) and add the JAR to your project’s build path or classpath.

---

## Running the Application

1. Compile all `.java` files:

```bash
javac BankingApp.java User.java Accounts.java AccountManager.java
```

2. Run the main application:

```bash
java BankingApp
```

3. Follow the on-screen prompts to:
   - Register a new user
   - Login
   - Create bank accounts
   - Credit, debit, transfer money
   - Check balance
   - Exit the application

---

## Important Notes

- All sensitive inputs like passwords and PINs are stored **in plaintext** in the database. For production, **implement proper hashing (e.g., BCrypt)** for security.
- Input validations are minimal; additional checks (e.g., email format, password strength) are recommended.
- This is a **console application** intended for educational purposes, not production use.
- Transaction management ensures atomicity for money transfer operations.

---

## Possible Enhancements

- Implement password and PIN hashing.
- Add transaction history logs.
- Add input validation and exception handling improvements.
- Implement a GUI (Swing/JavaFX) or a web interface.
- Add unit and integration tests.

---
This project was created following tutorials by Indian Programmer.


**Thank you for using the Banking System!**
