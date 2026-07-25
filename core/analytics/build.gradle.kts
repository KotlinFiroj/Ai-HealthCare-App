plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.analytics"
}

dependencies {
    implementation(project(":core:common"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)
}
