# Student Accommodation Microservices System
A microservices-based application for managing student accommodations across universities and dormitories. 
This system handles the management of students, universities, dormitory buildings, rooms, and reservations, ensuring efficient and secure housing operations.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture Diagram](#architecture-diagram)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Keycloak Setup](#keycloak-setup)

## Features
- University management
- Student management
- Reservation management
- Dormitory buildings, blocks, and room management
- Single Sign-On (SSO) with OpenID Connect
- Role-Based Access Control (RBAC)


## Tech Stack
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00758F?style=for-the-badge&logo=mysql&logoColor=white)
![H2](https://img.shields.io/badge/H2-1A73E8?style=for-the-badge&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)

## Architecture Diagram
![System Architecture](Global_architecture_diagram.png)
*This diagram illustrates the high-level microservices architecture, including API Gateway, Auth via Keycloak, and communication between services.*

## Prerequisites
* Docker
* Git

## Getting Started
These instructions will get you a copy of the project up and running on your local machine.
### Installation
1. Clone the repository:
``` 
    git clone https://github.com/MedEzzedineBenZbiba/4Twin1-JS-Ninjas-student-accommodation-MS.git 
```
2. Navigate into the project directory:
 ```
   cd 4Twin1-JS-Ninjas-student-accommodation-MS
 ```
### Run the project 
1. Run the docker-compose file :
```
    docker-compose up -d
```
### Keycloak Setup
1. Open [Keycloak Admin Console](http://localhost:8180/)
2. Create a new **Realm** (e.g., `student_accommodation`)
    - Or update the `application.yml` file to match your Realm name
3. Create roles (e.g., `admin`, `user`)
4. Create users and assign roles






