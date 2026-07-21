plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mediai.enterprise.core.network"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization-json)
}
