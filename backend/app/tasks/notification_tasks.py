from app.core.celery_app import celery_app
from app.services.notification_service import NotificationService
from app.schemas.notification import PushNotification
import asyncio

@celery_app.task(name="app.tasks.notification_tasks.send_push_notification")
def send_push_notification_task(user_id: str, title: str, body: str, fcm_token: str):
    async def run():
        notification = PushNotification(
            user_id=user_id,
            title=title,
            body=body
        )
        await NotificationService.send_push(notification, fcm_token)
        return f"Notification sent to {user_id}"

    return asyncio.run(run())
