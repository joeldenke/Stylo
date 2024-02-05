package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        alias(libs.plugins.android.application)
      }

      configureAndroid()
      android {
        sourceSets["main"].apply {
          manifest.srcFile("src/androidMain/AndroidManifest.xml")
          res.srcDirs("src/androidMain/res", "src/commonMain/resources")
          resources.srcDirs("src/commonMain/resources")
        }

        defaultConfig {
          versionCode = libs.versions.versionCode.get().toInt()
          versionName = libs.versions.versionName.get()
        }

        //packaging {
        //  resources {
        //    excludes += "/META-INF/{AL2.0,LGPL2.1}"
        //  }
        //}
        buildTypes {
          getByName("release") {
            isMinifyEnabled = false
          }
        }
      }
    }
  }
}
