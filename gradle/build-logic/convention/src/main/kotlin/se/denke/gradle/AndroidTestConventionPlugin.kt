package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidTestConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        alias(libs.plugins.android.test)
      }

      configureAndroid()
    }
  }
}
