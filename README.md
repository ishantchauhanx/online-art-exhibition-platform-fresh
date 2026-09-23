# Online Art Exhibition Platform

This project is a Java web application for an art exhibition platform built using Java EE Servlets, JDBC, and MySQL. It supports role-based access for Admin, Artist, and Art Enthusiast users.

## Features

- User registration and login
- Role-based dashboards for Admin, Artist, and Enthusiast
- Artwork upload and catalog management
- Exhibition approval workflow
- Artwork purchase flow
- Feedback and rating submission
- Session validation and input validation

## Tech Stack

- Java 17
- Maven
- Servlets 5.0
- JDBC
- MySQL 8
- Bootstrap 5
- Apache Tomcat 9/10

## Folder Structure

```text
art-exhibition-platform/
├── src/
│   ├── main/
│   │   ├── java/com/artexhibit/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dao/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   └── util/
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       ├── *.jsp
│   └── test/
├── pom.xml
├── README.md
└── .gitignore
```

## Database Setup

1. Create a MySQL database named `art_exhibition_db`.
2. Run the SQL schema below:

```sql
CREATE DATABASE IF NOT EXISTS art_exhibition_db;
USE art_exhibition_db;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','ARTIST','ENTHUSIAST') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE artworks (
    art_id INT PRIMARY KEY AUTO_INCREMENT,
    artist_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    medium VARCHAR(100),
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    status ENUM('AVAILABLE','SOLD','IN_EXHIBITION') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (artist_id) REFERENCES users(user_id)
);

CREATE TABLE exhibitions (
    exhibit_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
    created_by INT NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

CREATE TABLE exhibition_artworks (
    exhibit_id INT NOT NULL,
    art_id INT NOT NULL,
    PRIMARY KEY (exhibit_id, art_id),
    FOREIGN KEY (exhibit_id) REFERENCES exhibitions(exhibit_id),
    FOREIGN KEY (art_id) REFERENCES artworks(art_id)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    buyer_id INT NOT NULL,
    artwork_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM('SUCCESS','FAILED','PENDING') DEFAULT 'PENDING',
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (artwork_id) REFERENCES artworks(art_id)
);

CREATE TABLE feedback (
    feedback_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    artwork_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (artwork_id) REFERENCES artworks(art_id)
);
```

## Configuration

Update database credentials in `src/main/java/com/artexhibit/config/DBConnection.java`.

The application reads credentials from environment variables so secrets are not committed:

```powershell
$env:ART_DB_USERNAME = "root"
$env:ART_DB_PASSWORD = "your_mysql_password"
```

For a local MySQL installation with no password, leave `ART_DB_PASSWORD` empty. Set these variables before starting Tomcat.

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/art_exhibition_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
```

## Run the Application

```bash
mvn clean package
```

Then deploy the generated WAR file from `target/art-exhibition-platform.war` to Apache Tomcat 9/10.

Open:

```text
http://localhost:8080/art-exhibition-platform/login.jsp
```

## Default Roles

- Admin
- Artist
- Enthusiast

## Academic Alignment

This project matches the Java Web Project rubric:

- **Problem understanding and solution design:** MVC separation, role-based workflows, and relational schema design.
- **Core Java concepts:** POJO encapsulation, inheritance from `HttpServlet` and `Exception`, checked exception handling, `List<T>` collections, generics, and the polymorphic `Repository<T>` interface implemented by `UserDAO` and `ArtworkDAO`.
- **JDBC database integration:** `DBConnection`, `PreparedStatement`, try-with-resources, foreign keys, and dedicated DAO classes.
- **Servlet-based web integration:** HTTP request/response handling, `HttpSession` role checks, JSP views, redirects, validation, and servlet mappings.

The separate Java GUI rubric is not applicable because this is a Servlet/JSP web application rather than a Swing or JavaFX desktop application.

## Notes

This is a complete starter project for academic evaluation and can be extended with additional modules like exhibition request forms, admin metrics, and order history dashboards.
