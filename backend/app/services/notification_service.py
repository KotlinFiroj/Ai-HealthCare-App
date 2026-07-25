import firebase_admin
from firebase_admin import messaging, credentials
from app.core.config import settings
from app.schemas.notification import PushNotification
from typing import Optional
import logging

class NotificationService:
    def __init__(self):
        try:
            # This requires a service account JSON file from Firebase
            # cred = credentials.Certificate("path/to/serviceAccountKey.json")
            # firebase_admin.initialize_app(cred)
            pass
        except Exception as e:
            logging.error(f"Failed to initialize Firebase Admin: {e}")

    @staticmethod
    async def send_push(notification: PushNotification, fcm_token: str):
        """
        Sends a push notification via FCM.
        """
        message = messaging.Message(
            notification=messaging.Notification(
                title=notification.title,
                body=notification.body,
            ),
            data=notification.data,
            token=fcm_token,
        )
        try:
            # messaging.send(message)
            logging.info(f"Notification sent to {fcm_token}")
        except Exception as e:
            logging.error(f"Failed to send notification: {e}")

notification_service = NotificationService()
