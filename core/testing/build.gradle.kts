plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.testing"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso-core)
}
