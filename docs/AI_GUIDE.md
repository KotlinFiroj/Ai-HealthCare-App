# MediAI Enterprise: AI Engineering Guide

## Model
- **Engine**: Gemini 1.5 Flash/Pro.
- **SDK**: Google Generative AI SDK for Android.

## AI Workflows

### 1. Autonomous Agentic Flow
- **Orchestrator**: A central Gemini-powered agent that receives user intents.
- **Function Calling**: The agent autonomously selects Python tools (e.g., `search_doctors`) to satisfy requests.
- **Specialists**: Sub-agents for Diagnostics and Appointments handle refined reasoning.

### 2. Retrieval-Augmented Generation (RAG)
- Used in the Medical Chatbot.
- Injects relevant context from a local knowledge base (WHO guidelines, hospital policies) into the LLM prompt to ground answers and reduce hallucinations.

## Prompt Engineering Strategy
We use highly structured "System Instructions" that define the AI's persona, safety constraints, and output format (JSON). This ensures predictable and parsable integration with our backend services.

## Evaluation Pipeline
The `AiEvaluator` scores responses based on:
1. **Clinical Accuracy**: Matching retrieved context.
2. **Safety Compliance**: Presence of disclaimers.
3. **Emergency Sensitivity**: Correct redirection to SOS.

## AI Safety & Guardrails
- **Disclaimers**: Every AI response includes a mandatory medical disclaimer.
- **Confidence Scores**: AI outputs include confidence metrics for transparency.
- **System Prompts**: Highly structured prompts ensure clinical tone and preventive focus.
