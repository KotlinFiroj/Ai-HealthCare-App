# Tasks - Phase 26: Analytics, Notifications & Admin Services

- `[x]` Update User Model
    - `[x]` Add `is_admin` and `fcm_token` to `User` model in `user.py`
- `[x]` Define API Schemas
    - `[x]` `analytics.py`
    - `[x]` `notification.py`
- `[x]` Implement Service Layer
    - `[x]` `analytics_service.py`
    - `[x]` `notification_service.py` (FCM Integration)
    - `[x]` `medicine_service.py`
- `[x]` Implement API Endpoints
    - `[x]` `analytics.py` (GET /stats)
    - `[x]` `admin.py` (Doctor & Knowledge management)
- `[x]` Register Routers in `main.py`
- `[x]` Implement Background Notification Task
    - `[x]` Add `send_push_notification_task` to Celery
- `[ ]` Verify Admin Access and Analytics Aggregation
