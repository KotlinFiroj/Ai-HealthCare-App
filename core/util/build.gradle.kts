plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.util"
}

dependencies {
    implementation(project(":core:common"))
}
