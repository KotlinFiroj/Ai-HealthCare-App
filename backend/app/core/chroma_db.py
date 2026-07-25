import chromadb
from chromadb.config import Settings
from app.core.config import settings

class ChromaManager:
    def __init__(self):
        self.client = chromadb.HttpClient(
            host="chromadb", # Matched to docker-compose service name
            port=8000,
            settings=Settings(allow_reset=True)
        )

    def get_or_create_collection(self, name: str):
        return self.client.get_or_create_collection(name=name)

chroma_manager = ChromaManager()
