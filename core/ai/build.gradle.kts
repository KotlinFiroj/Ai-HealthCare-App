plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.ai"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.google.generativeai)
    implementation(libs.google.mlkit.ocr)
}
