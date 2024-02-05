package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinAndroidConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        alias(libs.plugins.kotlin.android)
      }

      configureKotlin()
    }
  }
}
