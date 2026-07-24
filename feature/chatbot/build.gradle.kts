plugins {
    id("mediai.android.library")
    id("mediai.android.compose")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.feature.chatbot"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ai"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
}
