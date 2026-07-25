import google.generativeai as genai
from typing import List, Optional, Callable, Any
from app.core.config import settings

class BaseAgent:
    """
    [BaseAgent]
    Base class for all specialized AI agents.
    Handles tool registration and basic Gemini communication.
    """
    def __init__(self, role: str, system_prompt: str, tools: Optional[List[Callable]] = None):
        self.role = role
        self.system_prompt = system_prompt
        self.tools = tools or []

        # Configure model with tools if provided
        self.model = genai.GenerativeModel(
            model_name='gemini-1.5-flash',
            tools=self.tools,
            system_instruction=self.system_prompt
        )
        self.chat_session = self.model.start_chat(enable_automatic_function_calling=True)

    async def execute(self, user_query: str) -> str:
        """
        Sends user query to the agent and returns the response.
        Automatic function calling handles tool execution internally.
        """
        response = self.chat_session.send_message(user_query)
        return response.text
