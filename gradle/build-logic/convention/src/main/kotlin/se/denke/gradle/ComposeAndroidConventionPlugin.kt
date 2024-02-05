package se.denke.gradle

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformAndroidConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      alias(libs.plugins.android.library)
      alias(libs.plugins.jetbrains.compose)
      alias(libs.plugins.kotlin.multiplatform)
    }
    extensions.configure<LibraryExtension> {
      configureAndroidCompose(this)
    }
    extensions.configure<KotlinMultiplatformExtension> {
      configureAndroidComposeMultiplatform(this)
    }
  }
}

internal fun Project.configureAndroidComposeMultiplatform (extension: KotlinMultiplatformExtension) {
  with(extension) {
    sourceSets {
      androidMain {
        dependencies {
          // Check BOM version is same compose version that CMP using
          val composeBom = libs.androidx.compose.bom.asProvider().get()
          implementation(project.dependencies.platform(composeBom))
          implementation(libs.androidx.compose.bom.ui.tooling)
          implementation(libs.androidx.compose.bom.ui.tooling.preview)
        }
      }
    }
  }
}

internal fun Project.configureAndroidCompose (
  commonExtension: CommonExtension<*, *, *, *, *>,
) {
  commonExtension.apply {

    buildFeatures {
      compose = true
    }

    composeOptions {
      kotlinCompilerExtensionVersion = libs.versions.jetbrains.compose.compiler.get()
    }
  }
}