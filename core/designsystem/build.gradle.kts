plugins {
    id("mediai.android.library")
    id("mediai.android.compose")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.designsystem"
}

dependencies {
    implementation(project(":core:common"))
}
