plugins {
    id("mediai.android.library")
    id("mediai.android.compose")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.feature.settings"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:data"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
}
