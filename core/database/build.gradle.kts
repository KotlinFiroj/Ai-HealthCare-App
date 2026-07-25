plugins {
    id("mediai.android.library")
    id("mediai.android.room")
    id("mediai.android.hilt")
}

android {
    namespace = "com.mediai.enterprise.core.database"
}

dependencies {
    implementation(project(":core:common"))
    androidTestImplementation(project(":core:testing"))
}
