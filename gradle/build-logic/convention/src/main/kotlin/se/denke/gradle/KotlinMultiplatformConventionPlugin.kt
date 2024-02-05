package se.denke.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      alias(libs.plugins.kotlin.multiplatform)
    }

    extensions.configure<KotlinMultiplatformExtension> {
      applyDefaultHierarchyTemplate()

      if (pluginManager.hasPlugin(libs.plugins.android.library.get().pluginId) ||
        pluginManager.hasPlugin(libs.plugins.android.application.get().pluginId)) {
        androidTarget()
      }

      @OptIn(ExperimentalWasmDsl::class)
      wasmJs {
        moduleName = "ComposeApp"
        browser {
          commonWebpackConfig {
            outputFileName = "composeApp.js"
          }
        }
        binaries.executable()
      }

      jvm("desktop")

      iosX64()
      iosArm64()
      iosSimulatorArm64()

      targets.withType<KotlinNativeTarget>().configureEach {
        binaries.configureEach {
          // Add linker flag for SQLite. See:
          // https://github.com/touchlab/SQLiter/issues/77
          linkerOpts("-lsqlite3")

          // Workaround for https://youtrack.jetbrains.com/issue/KT-64508
          freeCompilerArgs += "-Xdisable-phases=RemoveRedundantCallsToStaticInitializersPhase"
        }

        compilations.configureEach {
          compilerOptions.configure {
            // Try out preview custom allocator in K/N 1.9
            // https://kotlinlang.org/docs/whatsnew19.html#preview-of-custom-memory-allocator
            freeCompilerArgs.add("-Xallocator=custom")

            // https://kotlinlang.org/docs/whatsnew19.html#compiler-option-for-c-interop-implicit-integer-conversions
            freeCompilerArgs.add("-XXLanguage:+ImplicitSignedToUnsignedIntegerConversion")

            // Enable debug symbols:
            // https://kotlinlang.org/docs/native-ios-symbolication.html
            freeCompilerArgs.add("-Xadd-light-debug=enable")

            // Various opt-ins
            freeCompilerArgs.addAll(
              "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
              "-opt-in=kotlinx.cinterop.BetaInteropApi"
            )
          }
        }
      }

      targets.configureEach {
        sourceSets.all {
          languageSettings.optIn("kotlin.ExperimentalUnsignedTypes")
        }
        compilations.configureEach {
          compilerOptions.configure {
            freeCompilerArgs.add("-Xexpect-actual-classes")
            freeCompilerArgs.addAll(
              "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
              "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
              "-opt-in=org.jetbrains.compose.resources.ExperimentalResourceApi",
              "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
              "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
              "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
              "-opt-in=kotlinx.coroutines.FlowPreview",
            )
          }
        }
      }

      configureKotlin()
    }
  }
}

fun Project.addKspDependencyForAllTargets(dependencyNotation: Any) = addKspDependencyForAllTargets("", dependencyNotation)
fun Project.addKspTestDependencyForAllTargets(dependencyNotation: Any) = addKspDependencyForAllTargets("Test", dependencyNotation)

private fun Project.addKspDependencyForAllTargets(
  configurationNameSuffix: String,
  dependencyNotation: Any,
) {
  val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()
  dependencies {
    kmpExtension.targets
      .asSequence()
      .filter { target ->
        // Don't add KSP for common target, only final platforms
        target.platformType != KotlinPlatformType.common
      }
      .forEach { target ->
        add(
          "ksp${target.targetName.capitalized()}$configurationNameSuffix",
          dependencyNotation,
        )
      }
  }
}
