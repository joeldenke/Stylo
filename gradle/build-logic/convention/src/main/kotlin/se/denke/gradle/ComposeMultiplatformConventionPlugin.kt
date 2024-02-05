package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    pluginManager.alias(libs.plugins.jetbrains.compose)
    configureCompose()
  }
}

fun Project.configureCompose() {
  compose {
    kotlinCompilerPlugin.set(libs.versions.jetbrains.compose.compiler)
  }

  val composeVersion = libs.versions.jetbrains.compose.asProvider().get()
  configurations.configureEach {
    resolutionStrategy.eachDependency {
      val group = requested.group

      when {
        group.startsWith(libs.plugins.jetbrains.compose.get().pluginId) && !group.endsWith("compiler") -> {
          useVersion(composeVersion)
        }
      }
    }
  }
}

fun Project.compose(block: ComposeExtension.() -> Unit) {
  extensions.configure<ComposeExtension>(block)
}
