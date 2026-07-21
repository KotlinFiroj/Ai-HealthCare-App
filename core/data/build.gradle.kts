plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
}
