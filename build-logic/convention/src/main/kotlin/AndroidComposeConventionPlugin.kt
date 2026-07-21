import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<BaseExtension>()
            extension.apply {
                buildFeatures.compose = true
            }

            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("androidTestImplementation", platform(bom))

                add("implementation", libs.findLibrary("androidx-ui").get())
                add("implementation", libs.findLibrary("androidx-ui-graphics").get())
                add("implementation", libs.findLibrary("androidx-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-material3").get())
                add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("androidx-ui-test-manifest").get())
                add("androidTestImplementation", libs.findLibrary("androidx-ui-test-junit4").get())
            }
        }
    }
}
