package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

class AndroidLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        alias(libs.plugins.android.library)
      }
      configureAndroid()
      android {
        sourceSets["main"].apply {
          manifest.srcFile("src/androidMain/AndroidManifest.xml")
          res.srcDirs("src/androidMain/res", "src/commonMain/resources")
          resources.srcDirs("src/commonMain/resources")
        }
      }
    }
  }
}
