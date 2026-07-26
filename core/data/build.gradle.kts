plugins {
    id("mediai.android.library")
    id("mediai.android.hilt")
    id("com.google.protobuf")
}

android {
    namespace = "com.mediai.enterprise.core.data"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation(libs.androidx.datastore.proto)
    implementation(libs.protobuf.kotlin.lite)
}
