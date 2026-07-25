import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = 35
                defaultConfig {
                    targetSdk = 35
                    minSdk = 26
                    versionCode = 1
                    versionName = "1.0"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                signingConfigs {
                    create("release") {
                        storeFile = file("../release.jks")
                        storePassword = System.getenv("RELEASE_KEYSTORE_PASSWORD")
                        keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                        keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
                    }
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        signingConfig = signingConfigs.getByName("release")
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildFeatures {
                    buildConfig = true
                }
            }
        }
    }
}
