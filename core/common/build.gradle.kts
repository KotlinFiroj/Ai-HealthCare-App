plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines-android)
}
