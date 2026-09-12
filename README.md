# CareTrace

A backend system for a dementia-care ecosystem that helps patients and caretakers manage patient information, medications, routines, reminders, and important people.

The project is being developed as a **Spring Boot monolithic backend** with MongoDB and JWT-based authentication.

---

## 🚀 Features

### Authentication & User Management

* User registration and login
* JWT-based authentication
* Role-based users:

    * `PATIENT`
    * `CARETAKER`
* Password hashing using BCrypt
* Caretaker and patient account management

### Patient Management

Caretakers can:

* Create patients
* View patient information
* Update patient information
* Associate patients with their caretaker

Each patient has:

* Name
* Email
* Age
* Preferred language
* Associated caretaker

### Important People

Caretakers can manage important people associated with a patient.

Supported operations:

* Add important person
* View important people
* Update important person
* Delete important person

Information includes:

* Name
* Relationship
* Phone number
* Photo

Photos are currently stored directly in MongoDB as binary data (`byte[]`).

### Medications

Caretakers can manage medications for their patients.

Each medication contains:

* Name
* Dosage
* Frequency
* Start date
* End date
* Instructions

Supported operations:

* Create medication
* View patient medications
* Update medication
* Delete medication

### Routines

Patients can have personalized daily routines.

Each routine contains:

* Title
* Description
* Time
* Days of the week

Supported operations:

* Create routine
* View routines
* Update routine
* Delete routine

### Reminders

Reminders can be associated with either a medication or a routine.

Supported reminder types:

* `MEDICATION`
* `ROUTINE`
* `APPOINTMENT`
* `CUSTOM`

A reminder stores scheduling information and references the related medication or routine instead of duplicating their information.

For example:

```text
Reminder
    |
    └── medicationId
             |
             └── Medication
                  ├── name
                  ├── dosage
                  └── instructions
```

This allows the notification system to retrieve the relevant medication/routine information when generating a reminder.

---

# 🛠️ Tech Stack

| Technology          | Purpose                        |
| ------------------- | ------------------------------ |
| Java 21             | Programming language           |
| Spring Boot         | Backend framework              |
| Spring Security     | Authentication & authorization |
| JWT                 | Token-based authentication     |
| MongoDB             | Database                       |
| Spring Data MongoDB | Database interaction           |
| Maven               | Dependency management & build  |
| BCrypt              | Password hashing               |

---

# 📁 Project Structure

The project follows a simple layered structure:

```text
src
└── main
    └── java
        └── ...
            ├── controller
            ├── service
            ├── model
            ├── repository
            ├── dto
            ├── security
            └── exception
```

The project intentionally uses a simple structure rather than separating every feature into its own package.

---

# 🔐 Authentication

The API uses JWT authentication.

### Signup

```http
POST /auth/signup
```

### Signin

```http
POST /auth/signin
```

After successful login, the server returns a JWT.

For protected endpoints, send the token using:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 📡 API Endpoints

## Authentication

```text
POST   /auth/signup
POST   /auth/signin
```

---

## Patient

```text
POST   /patient
GET    /patient/{patientId}
PUT    /patient/{patientId}
```

---

## Important People

```text
POST   /patient/{patientId}/people
GET    /patient/{patientId}/people
PUT    /patient/{patientId}/people/{personId}
DELETE /patient/{patientId}/people/{personId}
```

The add-person endpoint uses `multipart/form-data` because it can contain an image.

---

## Medications

```text
POST   /patient/{patientId}/medications
GET    /patient/{patientId}/medications
PUT    /patient/{patientId}/medications/{medicationId}
DELETE /patient/{patientId}/medications/{medicationId}
```

---

## Routines

```text
POST   /patient/{patientId}/routines
GET    /patient/{patientId}/routines
PUT    /patient/{patientId}/routines/{routineId}
DELETE /patient/{patientId}/routines/{routineId}
```

---

## Reminders

```text
POST   /patient/{patientId}/reminders
GET    /patient/{patientId}/reminders
PUT    /patient/{patientId}/reminders/{reminderId}
DELETE /patient/{patientId}/reminders/{reminderId}
```

---

# 🗄️ Database

AeroCare uses MongoDB.

The main collections currently include:

```text
clients
patients
care_takers
important_people
medications
routines
reminders
```

The exact collection names depend on the `@Document` annotations in the models.

---

# ⚙️ Configuration

The application uses `application.properties`.

Example:

```properties
spring.application.name=AeroCare

spring.data.mongodb.uri=${MONGODB_URI}

jwt.secret=${JWT_SECRET}
```

You should provide the required environment variables before running the application.

### Required environment variables

```text
MONGODB_URI
JWT_SECRET
```

Example:

```text
MONGODB_URI=mongodb://localhost:27017/aerocare
JWT_SECRET=your-secret-key
```

If you are using MongoDB Atlas, `MONGODB_URI` will contain your Atlas connection string instead.

**Do not commit your real MongoDB credentials or JWT secret to GitHub.**

---

# ▶️ How to Run

## 1. Clone the repository

```bash
git clone <YOUR_REPOSITORY_URL>
```

Move into the project:

```bash
cd CareTracev2
```

---

## 2. Make sure Java is installed

The project uses **Java 21**.

Check your Java version:

```bash
java --version
```

You should see Java 21.

---

## 3. Make sure MongoDB is available

You can either:

* Run MongoDB locally
* Use MongoDB Atlas

For a local MongoDB instance, make sure the MongoDB server is running.

---

## 4. Configure environment variables

Set:

```bash
export MONGODB_URI="your-mongodb-connection-string"
export JWT_SECRET="your-jwt-secret"
```

Or configure them using the environment configuration method used by your development setup.

---

## 5. Build the project

Using Maven:

```bash
./mvnw clean install
```

If the Maven wrapper is not available:

```bash
mvn clean install
```

---

## 6. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

Or:

```bash
mvn spring-boot:run
```

The application should start on:

```text
http://localhost:8080
```

---

# 🧪 Testing the API

You can use tools such as:

* Postman
* Insomnia
* cURL
* Frontend application

### Example login flow

First create a caretaker:

```http
POST /auth/signup
```

Then login:

```http
POST /auth/signin
```

Copy the returned JWT token.

For protected endpoints, add:

```http
Authorization: Bearer <TOKEN>
```

Then you can create and manage patients, medications, routines, reminders, and important people.

---

# 🔄 Current Development Roadmap

The project is being developed in phases.

### Phase 1 — Foundation & Authentication

* [x] Project setup
* [x] MongoDB
* [x] User management
* [x] Patient
* [x] Caretaker
* [x] JWT authentication
* [x] Role-based access

### Phase 2 — Patient Management

* [x] Patient profile
* [x] Important people
* [x] Medications
* [x] Routines
* [x] Reminders

### Phase 3 — AI Context Engine

* [ ] Patient context retrieval
* [ ] Context-aware AI queries
* [ ] Personalized AI responses
* [ ] Patient information aggregation

### Phase 4 — Safety

* [ ] Patient location
* [ ] Safe zones
* [ ] Geofencing
* [ ] Safety monitoring
* [ ] Alert engine

### Phase 5 — Communication

* [ ] Patient ↔ Caretaker communication
* [ ] Messaging
* [ ] Notifications
* [ ] Emergency communication

### Phase 6 — Cognitive Care

* [ ] Cognitive games
* [ ] Personalized activities
* [ ] Progress tracking

### Phase 7 — Advanced Features

Planned if time permits:

* [ ] Voice interaction
* [ ] Offline/edge AI
* [ ] Vector/semantic memory
* [ ] Multilingual AI/voice
* [ ] Wearable/IoT integration
* [ ] Advanced notifications

---

# 🔒 Security Considerations

The project currently implements:

* JWT authentication
* BCrypt password hashing
* Role-based access
* Caretaker-patient ownership validation
* Medication/routine ownership validation for reminders

Sensitive configuration such as:

```text
MONGODB_URI
JWT_SECRET
```

should never be committed to the repository.

---

# 📌 Project Status

AeroCare is currently in active development.

The core patient-management functionality has been implemented, including:

```text
Authentication
      ↓
Patient Management
      ↓
Important People
      ↓
Medications
      ↓
Routines
      ↓
Reminders
```

The next major milestone is the **AI Context Engine**, which will allow the system to retrieve relevant patient information before generating personalized AI responses.
