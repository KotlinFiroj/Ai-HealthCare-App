plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.navigation"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation-compose)
}
