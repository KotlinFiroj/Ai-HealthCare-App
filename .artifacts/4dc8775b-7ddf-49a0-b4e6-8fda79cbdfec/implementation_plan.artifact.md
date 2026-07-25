# Implementation Plan - Phase 21: Backend Foundation & Database Architecture

Establish the server-side infrastructure for **MediAI Enterprise** using FastAPI and a robust PostgreSQL/ChromaDB data layer.

## User Review Required

> [!IMPORTANT]
> This phase transitions the project into a Full-Stack ecosystem.
>
> - **Tech Stack**: FastAPI (Python 3.11+), PostgreSQL (Relational), ChromaDB (Vector), Redis (Caching/Tasks).
> - **Containerization**: We will use Docker and Docker Compose to manage the microservices.
> - **Schema Design**: We will implement a highly normalized schema with audit trails for HIPAA compliance (simulated).

## Proposed Changes

### Backend Infrastructure (`backend/`)

#### [NEW] [Project Setup](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/backend)
- Initialize FastAPI project with standard directory structure (`app/api`, `app/core`, `app/models`, `app/services`).

#### [NEW] [docker-compose.yml](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/docker-compose.yml)
- Orchestrate containers for:
    - **FastAPI App** (Web server)
    - **PostgreSQL** (Primary DB)
    - **Redis** (Cache & Celery Broker)
    - **RabbitMQ** (Message Queue)
    - **ChromaDB** (Vector Store for RAG)

### Data Modeling (`backend/app/models`)

#### [NEW] [Base & Common](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/backend/app/models/base.py)
- Implement a `BaseModel` with `id`, `created_at`, `updated_at`, and `is_deleted`.

#### [NEW] [Healthcare Entities](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/backend/app/models/)
- `User`: Identity and Auth.
- `PatientProfile`: Medical history and vitals.
- `Doctor`: Specialist details and ratings.
- `Appointment`: Booking and status tracking.
- `MedicalRecord`: Reports, OCR results, and AI summaries.
- `Medication`: Prescription and reminder schedules.

### Core Configuration

#### [NEW] [database.py](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/backend/app/core/database.py)
- Configure SQLAlchemy with asynchronous session management.

#### [NEW] [config.py](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/backend/app/core/config.py)
- Pydantic-based settings for environment variables (DB_URL, GEMINI_API_KEY, etc.).

## Database Schema (ER Diagram)

```mermaid
erDiagram
    USER ||--o1 PATIENT_PROFILE : "has"
    USER ||--o{ APPOINTMENT : "books"
    DOCTOR ||--o{ APPOINTMENT : "attends"
    PATIENT_PROFILE ||--o{ MEDICAL_RECORD : "owns"
    PATIENT_PROFILE ||--o{ MEDICATION : "prescribed"
    MEDICAL_RECORD ||--o{ REPORT_ANALYSIS : "contains"

    USER {
        uuid id
        string email
        string hashed_password
        datetime created_at
    }

    PATIENT_PROFILE {
        uuid id
        uuid user_id
        string blood_group
        string allergies
    }

    MEDICAL_RECORD {
        uuid id
        uuid patient_id
        string title
        string category
        string file_url
        string ocr_text
    }
```

## Verification Plan

### Automated Tests
- Run database migrations and verify schema creation in PostgreSQL.
- Unit tests for SQLAlchemy models to ensure constraints and relationships.

### Manual Verification
- Verify Docker containers start correctly using `docker-compose up`.
- Inspect the FastAPI Swagger UI (`/docs`) to ensure the foundation is alive.
