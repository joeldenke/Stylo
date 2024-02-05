package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                gradleKotlinDsl()
                alias(libs.plugins.android.library)
                alias(libs.plugins.kotlin.multiplatform)
                alias(libs.plugins.stylo.kotlin.multiplatform)
                alias(libs.plugins.stylo.compose.multiplatform)
                alias(libs.plugins.stylo.compose.android)
            }
            configureAndroid()
            configureFeature()
        }
    }
}

fun Project.configureFeature() {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.commonMain {
            dependencies {
                //api(project(":core:common"))
                //api(project(":ui:base"))
            }
        }
    }
}