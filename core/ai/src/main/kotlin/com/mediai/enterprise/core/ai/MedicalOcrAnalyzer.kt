package com.mediai.enterprise.core.ai

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [MedicalOcrAnalyzer]
 * Uses Google ML Kit to extract text from images (Medical Reports/Prescriptions).
 */
@Singleton
class MedicalOcrAnalyzer @Inject constructor() {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun extractText(bitmap: Bitmap): Result<String> {
        return try {
            val image = InputImage.fromBitmap(bitmap, 0)
            val result = recognizer.process(image).await()
            Result.success(result.text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
