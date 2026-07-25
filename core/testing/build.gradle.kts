plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.testing"
}

dependencies {
    implementation(project(":core:common"))

    api(libs.junit)
    api(libs.mockk)
    api(libs.mockk.android)
    api(libs.turbine)
    api(libs.kotlinx.coroutines.test)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
}
