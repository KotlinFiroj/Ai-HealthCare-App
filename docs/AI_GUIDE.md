# MediAI Enterprise: AI Engineering Guide

## Model
- **Engine**: Gemini 1.5 Flash/Pro.
- **SDK**: Google Generative AI SDK for Android.

## AI Workflows

### 1. Retrieval-Augmented Generation (RAG)
- Used in the Medical Chatbot.
- Injects relevant context from a local knowledge base (WHO guidelines, hospital policies) into the LLM prompt to ground answers and reduce hallucinations.

### 2. Medical Report Summarization
- Analyzes raw OCR text from Blood Tests, MRI, and prescriptions.
- Provides plain English summaries, risk detection, and follow-up questions.

### 3. Diagnostic-Assist (Symptom Checker)
- Performs probabilistic analysis of symptoms.
- Emergency Detection logic automatically redirects critical cases to the SOS module.

## AI Safety & Guardrails
- **Disclaimers**: Every AI response includes a mandatory medical disclaimer.
- **Confidence Scores**: AI outputs include confidence metrics for transparency.
- **System Prompts**: Highly structured prompts ensure clinical tone and preventive focus.
