# Spring Boot + Angular Full Stack Practice

## Goal & Purpose

The goal is to gain repetition ("reps") building complete applications from scratch and reinforce full-stack development concepts.

---

## Project Creation Strategy

This repository is used to practice building full-stack applications using:

- Spring Boot
- Angular
- TypeScript
- REST APIs
- Databases
- Postman
- GitHub Actions (future)
- Docker (future)

There are different ways to create Spring Boot + Angular projects. One option I recommend is:

### 1. Backend First

Create the Spring Boot API before building the frontend.

Benefits:

- Easier debugging
- Easier testing
- Can validate endpoints before creating UI

#### Create Spring Boot Project

Using Maven:

##### VS Code

```text
Command + Shift + P
```

Requirements:

- MUST HAVE SPRING EXTENSION INSTALLED

Then search:

```text
Spring Initializr: Create Maven Project
```

##### Alternative

Use Spring Initializr website:

```text
https://start.spring.io
```

Download the project and open it in your IDE.

Make sure you are in the desired folder before generating the project.

---

### 2. Frontend Next

Build the Angular frontend after the backend API is working.

Technologies:

- Angular
- TypeScript
- HTML
- CSS
- JavaScript (Angular uses TypeScript which compiles to JavaScript)

#### Install Angular CLI

```bash
npm install -g @angular/cli
```

#### Verify Installation

```bash
ng version
```

#### Create Project

```bash
ng new recipe-manager-ui
```

#### Run Project

```bash
ng serve
```

#### Default URL

```text
http://localhost:4200
```

---

## Recommended Spring Boot Dependencies

### Spring Web

Used for:

- REST Controllers
- Endpoints
- HTTP Requests

### Spring Boot DevTools

Used for:

- Auto restart while coding
- Faster development workflow

### Spring Data JPA

Used for:

- Repositories
- Database access
- ORM functionality

### H2 Database

Used for:

- Local development database
- Testing CRUD operations

### Lombok

Used for reducing boilerplate code.

Examples:

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
```

### Validation

Used for request validation.

Examples:

```java
@NotBlank
@Size
```

---

## pom.xml

When using Spring Initializr, it will ask which dependencies to include.

Spring Boot automatically generates a:

```text
pom.xml
```

file.

The pom.xml file contains:

- Dependencies
- Plugins
- Java Version
- Build Configuration

You can:

- Add dependencies
- Remove dependencies
- Update dependency versions
- Configure plugins

inside the pom.xml file.

---

## Java Version

The pom.xml file also specifies the Java version used by the project.

Check your installed Java version:

```bash
java --version
```

or

```bash
java -version
```

---

## Maven Version

Check installed Maven version:

```bash
mvn --version
```

---

## Planned Architecture

```text
User
↓
Angular Component
↓
Angular Service
↓
HTTP Request
↓
Spring REST Controller
↓
Service Layer
↓
Repository Layer
↓
Database
```

---
## API Testing With Postman

Postman is used to test the Spring Boot backend before connecting it to Angular.

This helps verify that the API works independently from the frontend.

### Why Use Postman?

Postman helps test:

- Endpoints
- HTTP methods
- Request bodies
- Response bodies
- Status codes
- Headers
- Authentication tokens

---

## Common API Requests

### Create Recipe

```http
POST /recipes
```

## Future Topics

- PostgreSQL
- Spring Security
- JWT Authentication
- Docker
- GitHub Actions
- CI/CD Pipelines
- DevSecOps Concepts
- Cloud Deployment

## 1. What am I building?
### What data do I need?
#### Recipe
- id
- name
- description
- ingredients
- instructions

-> Recipe Entity

## 2. What CRUD operations exist?
### What can a user do?
#### Create Recipe
- Create Recipe
- Read Recipe
- Update Recipe
- Delete Recipe

Now map to REST endppoints:
- POST      /recipes
- GET       /recipes
- GET       /recipes/{id}
- PUT       /recipes/{id}
- DELETE    /recipes/{id}

## 3. What layers do I need?
Controller(http Requests): controller/
↓
Service(Business Logic): service/
↓
Repository(CRUD Operations): repository/
↓
Database(SQL database): model/

### When creating layers
Usually layers sit in src/main/java/com/recipestore/recipe_manager_api. Right click and click on:

"New Java Package" -> ex: "com.recipestore.recipe_manager_api.controller" or type "controller"

### Why?
So Spring and Java know exactly where everything lives.

### What about resources?
This is where:
- application.properties
- application.yml
- static/
- templates/

### QUICK RULE
If it's Java code:
- src/main/java

If it's configuration:
- src/main/resources


### Markdown Syntax
This README.md file was created using markdown syntax.