# VidyaSarthi — College Learning & Management Portal (Backend)

> A production-ready RESTful backend for a college administration and learning platform, built with **Java 21** and **Spring Boot 3.5**.

---

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Environment Configuration](#environment-configuration)
- [API Endpoints](#api-endpoints)
- [Security Architecture](#security-architecture)
- [Database Schema (Overview)](#database-schema-overview)

---

## Overview

VidyaSarthi is the backend service for a college portal that supports three user roles — **Admin**, **Faculty**, and **Student**. It handles authentication, academic hierarchy management, study material distribution, feedback collection, analytics, and more.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.5 |
| Security | Spring Security + OAuth2 Resource Server (RSA JWT) |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Mapping | MapStruct 1.6.3 |
| Boilerplate | Lombok |
| Email | Spring Mail (Gmail SMTP) + Thymeleaf Templates |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |

---

## Features

### Authentication & Security
- Stateless JWT authentication using auto-generated **RSA key pairs**
- **Token blacklisting** on logout to prevent reuse of invalidated tokens
- **OTP-based email verification** for student signup (2-step flow)
- **OTP-based password reset** (initiate → verify → reset)
- Role-based access control: `Admin`, `Faculty`, `Student`
- BCrypt password hashing

### User Management (Admin)
- Add / remove Students and Faculty
- Profile photo upload (multipart, up to 5 MB)
- Filter students by branch, year, semester
- Filter faculty by designation and subject
- View full faculty list with assigned subjects

### Academic Hierarchy
- Full CRUD for: **Regulation → Branch → Semester → Subject → Unit**
- Subject-code-based lookups for efficient material linking

### Study Materials
- Upload and manage **Notes, PYQs, Question Banks**
- Materials linked to Subject, Unit, and uploading Faculty
- Supports file uploads up to 50 MB

### Feedback & Surveys
- Teacher feedback submission and retrieval
- Student exit survey with completion status tracking

### Analytics & News
- Visit logging for site analytics
- Admin dashboard analytics endpoint
- Faculty-published news feed for announcements

### Developer Experience
- Swagger UI at `/swagger-ui.html`
- Structured `ResponseStructure<T>` wrapper for all API responses
- CORS pre-configured for React frontend (`localhost:5173`, `localhost:5174`)

---

## Project Structure

```
VidyaSarthi/
└── src/main/java/edu/js/project/
    ├── configure/          # Security config, RSA key generation, AppConfig
    ├── controller/         # REST controllers (Auth, Admin, Student, Faculty, Analytics, etc.)
    ├── dto/                # Request/Response DTOs
    ├── entity/             # JPA entities (Users, Student, Teacher, Material, etc.)
    ├── NewEntities/        # Refactored entities (NewTeacher, NewSubject, etc.)
    ├── enums/              # BranchType, MaterialType, ComplainStatus, etc.
    ├── repository/         # Spring Data JPA repositories
    ├── responseStructure/  # Standard response wrappers
    ├── service/            # Service interfaces
    │   └── impl/           # Service implementations (UserService, OtpService, EmailService, etc.)
    └── utility/            # MapStruct Mapper
```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8+

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Furquan-Shaikh/backend_Vidya2.git
   cd backend_Vidya2/VidyaSarthi
   ```

2. **Create the MySQL database**
   ```sql
   CREATE DATABASE VidyaSarthi;
   ```

3. **Configure environment** — see [Environment Configuration](#environment-configuration)

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access Swagger UI**
   ```
   http://localhost:8080/swagger-ui.html
   ```

6. **Seed the Admin** (first-time setup only)
   ```
   GET http://localhost:8080/VidyaSarthi/addAdmin
   ```
   Default credentials: `admin@gmail.com` / `Pass@123`

---

## Environment Configuration

Update `src/main/resources/application.properties` with your values:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/VidyaSarthi
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# RSA Key Storage (auto-generated on first run)
app.security.rsa.keys.location=src/main/resources/keys
app.security.rsa.key-size=2048

# Email (Gmail SMTP)
spring.mail.username=YOUR_GMAIL_ADDRESS
spring.mail.password=YOUR_GMAIL_APP_PASSWORD
```

> **Note:** For Gmail, generate an [App Password](https://myaccount.google.com/apppasswords) — do not use your regular Gmail password.

---

## API Endpoints

### Public (No Auth Required)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/VidyaSarthi/initiateSignup` | Step 1: Send OTP to email for student signup |
| POST | `/VidyaSarthi/verifyOtpAndSignup` | Step 2: Verify OTP and create account |
| POST | `/VidyaSarthi/resendOtp` | Resend signup OTP |
| POST | `/VidyaSarthi/loginAcc` | Login — returns JWT token |
| POST | `/VidyaSarthi/initiateForgotPassword` | Send OTP for password reset |
| POST | `/VidyaSarthi/verifyForgotOtpAndReset` | Verify OTP and reset password |
| POST | `/VidyaSarthi/resendForgotOtp` | Resend forgot-password OTP |
| POST | `/VidyaSarthi/log-visit` | Log a site visit (analytics) |
| GET  | `/VidyaSarthi/getNewsList` | Get faculty news/announcements |
| GET  | `/VidyaSarthi/addAdmin` | Seed default admin (first-time setup) |

### Admin Only

| Method | Endpoint | Description |
|---|---|---|
| POST | `/VidyaSarthi/addStudent` | Add a student |
| POST | `/VidyaSarthi/addFaculty` | Add a faculty member |
| DELETE | `/VidyaSarthi/removeUser` | Remove a user by ID |
| GET | `/VidyaSarthi/facultyList` | List all faculty |
| GET | `/VidyaSarthi/studentList` | List all students |
| POST | `/VidyaSarthi/searchTeacherByFilter` | Filter faculty by designation/subject |
| POST | `/VidyaSarthi/searchStudentByFilter` | Filter students by branch/year/semester |
| GET | `/VidyaSarthi/admin/dashboard-analytics` | Admin analytics dashboard |

### Authenticated (Faculty / Student)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/VidyaSarthi/logoutAcc` | Logout (blacklists token) |
| POST | `/VidyaSarthi/student/updateStudent` | Update student profile + photo |
| POST | `/VidyaSarthi/faculty/updateFaculty` | Update faculty profile + photo |
| POST | `/VidyaSarthi/student/exit-survey` | Submit student exit survey |
| GET | `/VidyaSarthi/getFacultyListWithSubject` | Faculty list with subjects |
| POST | `/VidyaSarthi/searchUser` | Get user detail by ID |

---

## Security Architecture

```
Client Request
     │
     ▼
CORS Filter (pre-configured for React frontend)
     │
     ▼
JWT Bearer Token Validation (RSA Public Key)
     │
     ├─ Blacklist Check (in-memory via BlacklistToken)
     │
     ▼
Role-Based Authorization
     ├── Admin  → admin management endpoints
     ├── Faculty → /faculty/** endpoints
     └── Student → /student/** endpoints
```

- **JWT expiry:** 3600 seconds (1 hour)
- **OTP expiry:** 10 minutes
- **Keys:** RSA 2048-bit, auto-generated at startup and stored in `src/main/resources/keys/`

---

## Database Schema (Overview)

```
Users ──────────────┬── AdminClg
                    ├── Teacher
                    ├── NewTeacher
                    └── Student

Regulation ─── Branch ─── Semester ─── Subject ─── Unit ─── Material
                                                           (Notes/PYQ/QB)

NewTeacher ─── NewSubject (many-to-many)

TeacherFeedback, StudentExitSurvey, Complain, AnalyticsLog, News
```

---

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

---

## License

This project is for educational purposes.
