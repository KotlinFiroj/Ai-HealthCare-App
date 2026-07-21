plugins {
    id("mediai.android.library")
    id("mediai.android.compose")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(libs.coil.compose)
}
