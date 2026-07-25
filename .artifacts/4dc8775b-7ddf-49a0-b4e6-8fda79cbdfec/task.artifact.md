# Tasks - Phase 24: OCR & AI Services (Async Workers)

- `[x]` Update Infrastructure
    - `[x]` Add Celery worker to `docker-compose.yml`
- `[x]` Implement Core Task Engine
    - `[x]` Create `backend/app/core/celery_app.py`
    - `[x]` Create `backend/app/core/ai_engine.py` (Gemini integration)
- `[x]` Implement Background Tasks
    - `[x]` Create `backend/app/tasks/__init__.py`
    - `[x]` Create `backend/app/tasks/medical_tasks.py`
- `[x]` Implement API & Schemas
    - `[x]` Create `backend/app/schemas/report.py`
    - `[x]` Create `backend/app/services/report_service.py`
    - `[x]` Create `backend/app/api/v1/endpoints/reports.py`
- `[x]` Register Reports Router in `main.py`
- `[ ]` Verify Async Pipeline with Logs
