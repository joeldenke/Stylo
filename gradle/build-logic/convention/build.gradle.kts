plugins {
  `kotlin-dsl`
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(libs.versions.kotlinJvmToolchain.get().toInt())
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.compose.gradlePlugin)
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
  plugins {
    register("kotlinMultiplatform") {
      id = "se.denke.kotlin.multiplatform"
      implementationClass = "se.denke.gradle.KotlinMultiplatformConventionPlugin"
    }

    register("root") {
      id = "se.denke.root"
      implementationClass = "se.denke.gradle.RootConventionPlugin"
    }

    register("kotlinAndroid") {
      id = "se.denke.kotlin.android"
      implementationClass = "se.denke.gradle.KotlinAndroidConventionPlugin"
    }

    register("androidApplication") {
      id = "se.denke.android.application"
      implementationClass = "se.denke.gradle.AndroidApplicationConventionPlugin"
    }

    register("androidLibrary") {
      id = "se.denke.android.library"
      implementationClass = "se.denke.gradle.AndroidLibraryConventionPlugin"
    }

    register("androidTest") {
      id = "se.denke.android.test"
      implementationClass = "se.denke.gradle.AndroidTestConventionPlugin"
    }

    register("composeMultiplatform") {
      id = "se.denke.compose.multiplatform"
      implementationClass = "se.denke.gradle.ComposeMultiplatformConventionPlugin"
    }

    register("composeMultiplatformAndroid") {
      id = "se.denke.compose.android"
      implementationClass = "se.denke.gradle.ComposeMultiplatformAndroidConventionPlugin"
    }

    register("coodyFeature") {
      id = "se.denke.feature"
      implementationClass = "se.denke.gradle.FeatureConventionPlugin"
    }
  }
}
