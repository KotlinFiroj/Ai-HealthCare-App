plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.security"
}

dependencies {
    implementation(project(":core:common"))
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
}
