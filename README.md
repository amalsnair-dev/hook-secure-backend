# HOOK - Secure Message Sharing Backend

HOOK is a secure backend system built using Java, Spring Boot, Spring Security, JWT Authentication, and PostgreSQL.

The project provides secure user authentication, protected APIs, refresh token management, role-based authorization, and secure message sharing architecture.

---

# Features

## Authentication & Security
- User Registration
- User Login
- JWT Access Token Authentication
- Refresh Token System
- Logout & Token Revocation
- BCrypt Password Encryption
- Role-Based Access Control (RBAC)
- Spring Security Integration

## Secure API Architecture
- Protected APIs using JWT
- Public & Private Route Management
- Custom JWT Authentication Filter
- Secure Session Management

## Database Features
- PostgreSQL Integration
- JPA/Hibernate ORM
- Relational Database Design
- Entity Relationships

## Message System
- Secure Message Creation
- Shareable Token-Based Access
- Expiry-Based Message Access

---

# Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

## Database
- PostgreSQL

## Authentication
- JWT (JSON Web Token)
- Refresh Tokens
- BCrypt Password Encoder

## Tools
- Maven
- Postman
- Git
- GitHub

---

# Project Architecture

Frontend / Client
↓
Controller Layer
↓
Service Layer
↓
Security Layer
↓
Repository Layer
↓
PostgreSQL Database

---

# Security Flow

Client Request
↓
JWT Authentication Filter
↓
Token Validation
↓
Security Context Authentication
↓
Protected APIs
↓
Database Access

---

# Current Features Implemented

## User Module
- Register User
- Login User
- JWT Token Generation
- Refresh Token Management
- Logout System

## Security Module
- JWT Authentication Filter
- Custom UserDetailsService
- SecurityFilterChain Configuration
- Role-Based Authorization

## Message Module
- Create Secure Messages
- Store Tokens
- Expiry Validation

---

# Database Tables

| Table | Purpose |
|------|----------|
| users | Store application users |
| refresh_tokens | Manage login sessions |
| messages | Store secure messages |
| tokens | Manage secure sharing tokens |

---

# API Security

| API Type | Security |
|----------|-----------|
| Public APIs | permitAll() |
| Protected APIs | JWT Required |
| Admin APIs | ADMIN Role Required |

---


