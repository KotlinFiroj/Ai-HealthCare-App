import google.generativeai as genai
from app.core.config import settings
from app.core.chroma_db import chroma_manager
from typing import List
import uuid

class RagService:
    def __init__(self):
        if settings.GEMINI_API_KEY:
            genai.configure(api_key=settings.GEMINI_API_KEY)
        self.collection = chroma_manager.get_or_create_collection("medical_knowledge")

    async def embed_and_store(self, texts: List[str], metadatas: List[dict]):
        """
        Chunks and stores medical text into ChromaDB using Gemini Embeddings.
        """
        # In a real implementation, we'd use genai.embed_content
        # For simplicity in this phase, we use ChromaDB's default embedding function
        # or simulate it.
        ids = [str(uuid.uuid4()) for _ in texts]
        self.collection.add(
            documents=texts,
            metadatas=metadatas,
            ids=ids
        )

    def retrieve_context(self, query: str, n_results: int = 3) -> str:
        """
        Performs semantic search to find relevant medical context.
        """
        results = self.collection.query(
            query_texts=[query],
            n_results=n_results
        )

        # Flatten documents into a single string
        documents = results.get('documents', [[]])[0]
        return "\n\n---\n\n".join(documents)

    async def seed_knowledge_base(self):
        """
        Initializes the knowledge base with WHO guidelines and hospital policies.
        """
        if self.collection.count() > 0:
            return

        initial_data = [
            ("Hospital visiting hours are from 9:00 AM to 8:00 PM daily. Emergency department is open 24/7.", {"source": "hospital_policy"}),
            ("Diabetes management involves regular blood sugar monitoring, a healthy diet, and regular exercise.", {"source": "who_guidelines"}),
            ("For a scheduled surgery, patients must fast for at least 8 hours prior to the procedure.", {"source": "surgical_prep"}),
            ("Hypertension (high blood pressure) is often silent and requires regular screening.", {"source": "who_guidelines"}),
            ("All laboratory results take 24-48 hours to be processed and uploaded to the portal.", {"source": "lab_policy"}),
        ]

        texts = [item[0] for item in initial_data]
        metadatas = [item[1] for item in initial_data]
        await self.embed_and_store(texts, metadatas)

rag_service = RagService()
