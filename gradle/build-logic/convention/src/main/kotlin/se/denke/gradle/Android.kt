package se.denke.gradle

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.HasUnitTestBuilder
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAndroid() {
  android {
    compileSdkVersion(libs.versions.compileSdkVersion.get().toInt())

    defaultConfig {
      minSdk = libs.versions.minSdkVersion.get().toInt()
      targetSdk = libs.versions.targetSdkVersion.get().toInt()
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
      if (this@android is LibraryExtension) {
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
      }

      unitTests {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
      }
    }
  }

  androidComponents {
    beforeVariants(selector().withBuildType("release")) { variantBuilder ->
      (variantBuilder as? HasUnitTestBuilder)?.apply {
        enableUnitTest = false
      }
    }
  }
}

fun Project.android(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)

private fun Project.androidComponents(action: AndroidComponentsExtension<*, *, *>.() -> Unit) {
  extensions.configure(AndroidComponentsExtension::class.java, action)
}
